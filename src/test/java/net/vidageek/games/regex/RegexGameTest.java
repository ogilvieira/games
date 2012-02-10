package net.vidageek.games.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.vidageek.games.regex.task.RegexGame;
import net.vidageek.games.regex.test.infra.Person;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

final public class RegexGameTest {

	@Mock
	private Descriptions descriptions;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void allTasksMustHaveAnswers() {
		new Person(
				// TODO: Review this, strange control index
				"a", "b", "ab", "abc", "\\\\", "\\$", "abcdefg", "ab\\$cd\\^ef\\\\g", "[ab]", "[ab]d", "[abc]d",
				"[a-c]", "[a-cA-D]", "[0-2]", "\\d", "\\da", "[\\da]", "\\s", "\\sa", "[\\sa]", "\\w", "\\wp", ".",
				"[^ab]", "[^ab]d", "[^abc]d", "\\D", "\\Da", "[^\\da]", "\\S", "\\Sa", "[^\\sa]", "\\W", "\\Wp", "a?",
				"([a-z]+)", "([a-z]+).*", "([a-z]+)(.*)", "(([a-z]+)(.*)a)").play(new RegexGame(descriptions));
	}

	@Test
	public void hasNextTaskReturnsTrueIfThereIsSuchTask() {
		RegexGame game = new RegexGame(descriptions);
		assertTrue(game.hasNextTask(0));
	}

	@Test
	public void hasNextTaskReturnsFalseIfThereIsNoSuchTask() {
		RegexGame game = new RegexGame(descriptions);
		assertFalse(game.hasNextTask(game.getSize() + 1));
	}

	@Test
	public void nextTaskReturnsNextTask() {
		RegexGame game = new RegexGame(descriptions);
		assertEquals(4, game.nextTask(3));
	}

}

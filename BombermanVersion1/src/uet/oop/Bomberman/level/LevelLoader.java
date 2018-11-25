package uet.oop.Bomberman.level;

import uet.oop.Bomberman.Board;
import uet.oop.Bomberman.Board;
import uet.oop.Bomberman.exceptions.LoadLevelException;

import java.io.IOException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width = 20, _height = 20; // default values just for testing
	protected int _level;
	protected Board _board;

	public LevelLoader(Board board, int level) throws LoadLevelException {
		_board = board;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) throws LoadLevelException;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}

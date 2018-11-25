package uet.oop.Bomberman.entities.tile;

import uet.oop.Bomberman.entities.Entity;
import uet.oop.Bomberman.graphics.Screen;
import uet.oop.Bomberman.graphics.Sprite;
import uet.oop.Bomberman.level.Coordinates;

/**
 * Entity cố định, không di chuyển
 */
public abstract class Tile extends Entity {
	
	public Tile(int x, int y, Sprite sprite) {
		_x = x;
		_y = y;
		_sprite = sprite;
	}

	/**
	 * Mặc định không cho bất cứ một đối tượng nào đi qua
	 * @param e
	 * @return
	 */
	@Override
	public boolean collide(Entity e) {
		return false;
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity( Coordinates.tileToPixel(_x), Coordinates.tileToPixel(_y), this);
	}
	
	@Override
	public void update() {}
}

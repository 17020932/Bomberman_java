package uet.oop.Bomberman.entities.tile;

import uet.oop.Bomberman.GameSound.Sound;
import uet.oop.Bomberman.entities.Entity;
import uet.oop.Bomberman.entities.character.Bomber;
import uet.oop.Bomberman.graphics.Sprite;

public class Portal extends Tile {

	public Portal(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
		if(e instanceof Bomber) {
			((Bomber)e).handleCollidePortal();

			return true;
		}
		return false;
	}

}

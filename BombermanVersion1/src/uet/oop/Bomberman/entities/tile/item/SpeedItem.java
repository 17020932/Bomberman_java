package uet.oop.Bomberman.entities.tile.item;

import uet.oop.Bomberman.Game;
import uet.oop.Bomberman.GameSound.Sound;
import uet.oop.Bomberman.entities.Entity;
import uet.oop.Bomberman.entities.character.Bomber;
import uet.oop.Bomberman.graphics.Sprite;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if(e instanceof Bomber) {
			((Bomber)e).addItem(this);
			remove();
			Sound.getIstance().getAudio(Sound.ITEM).play();
			return true;
		}
		return false;
	}

	@Override
	public void setValues() {
		_active = true;
		Game.addBomberSpeed(0.2);
	}
}

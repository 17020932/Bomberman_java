package uet.oop.Bomberman.entities.character.enemy;

import uet.oop.Bomberman.Board;
import uet.oop.Bomberman.Game;
import uet.oop.Bomberman.entities.character.enemy.ai.AILow;
import uet.oop.Bomberman.graphics.Sprite;

public class Balloon extends Enemy {
	
	
	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 100);
		
		_sprite = Sprite.balloom_left1;
		
		_ai = new AILow();
		_direction = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
				break;
		}
	}
}

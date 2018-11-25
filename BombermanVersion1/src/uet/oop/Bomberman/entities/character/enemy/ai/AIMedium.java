package uet.oop.Bomberman.entities.character.enemy.ai;

import uet.oop.Bomberman.entities.character.Bomber;
import uet.oop.Bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		if(_bomber == null)
		return  random.nextInt(4);
		else {
			int tmp = random.nextInt(2);
			if(tmp == 1) {
				if(_bomber.getXTile() < _e.getXTile() )		return 3;
				else return 1;
			}
			else {
				if (_bomber.getYTile() < _e.getYTile() )		return 0;
				else return 2;
			}
		}
	}

}

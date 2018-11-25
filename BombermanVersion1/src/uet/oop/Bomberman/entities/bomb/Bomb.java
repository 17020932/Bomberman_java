package uet.oop.Bomberman.entities.bomb;

import uet.oop.Bomberman.Board;
import uet.oop.Bomberman.Game;
import uet.oop.Bomberman.GameSound.Sound;
import uet.oop.Bomberman.entities.AnimatedEntitiy;
import uet.oop.Bomberman.entities.Entity;
import uet.oop.Bomberman.entities.character.Bomber;
import uet.oop.Bomberman.entities.character.Character;
import uet.oop.Bomberman.graphics.Screen;
import uet.oop.Bomberman.graphics.Sprite;
import uet.oop.Bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	
	protected Board _board;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
			//Sound.getIstance().getAudio(Sound.BOMBER_DIE).play();
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}

    /**
     * Xử lý Bomb nổ
     */
	protected void explode() {
		_exploded = true;
		_allowedToPassThru = true;
		
		// TODO: xử lý khi Character đứng tại vị trí Bomb
		Character a = _board.getCharacterAtExcluding((int)_x, (int)_y, null);
		if(a!=null) {
			a.kill();
		}
		// TODO: tạo các Flame
		_flames = new Flame[4];

		for(int i=0; i<_flames.length; i++) {
			_flames[i] = new Flame((int) _x, (int) _y, i, Game.getBombRadius(), _board);
		}
		 Sound.getIstance().getAudio(Sound.BONG_BANG).play();
	}
	
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

	@Override
	public boolean collide(Entity e) {
        // TODO: xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThru)
        // TODO: xử lý va chạm với Flame của Bomb khác
		if(e instanceof Bomber) {
			double diffX = e.getX() - Coordinates.tileToPixel(getX());
			double diffY = e.getY() - Coordinates.tileToPixel(getY());

			if(!(diffX >= -10 && diffY < 16 && diffY >=1 && diffY <= 28)) {
				_allowedToPassThru = true;
			}

			return _allowedToPassThru;
		}

		if(e instanceof Flame) {
			explode();
			return true;
		}
        return false;
	}
}

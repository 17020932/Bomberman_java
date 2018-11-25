package uet.oop.Bomberman.entities.character.enemy;

import uet.oop.Bomberman.Board;
import uet.oop.Bomberman.Game;
import uet.oop.Bomberman.entities.character.enemy.ai.AILow;
import uet.oop.Bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Enemy{
    public Doll(int x, int y, Board board) {
            super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed()/2, 400);
            _sprite = Sprite.doll_left1;

            _ai = new AILow();

        Random r = new Random();

            if(canMove(-1, 0))  _direction = 1;
            else    _direction = r.nextInt(3);
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                else
                    _sprite = Sprite.doll_left1;
                break;
            case 2:
            case 3:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
                else
                    _sprite = Sprite.doll_left1;
                break;
        }

    }
}

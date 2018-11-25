package uet.oop.Bomberman.entities.character;

import uet.oop.Bomberman.Board;
import uet.oop.Bomberman.Game;
import uet.oop.Bomberman.GameSound.Sound;
import uet.oop.Bomberman.entities.Entity;
import uet.oop.Bomberman.entities.LayeredEntity;
import uet.oop.Bomberman.entities.bomb.Bomb;
import uet.oop.Bomberman.entities.bomb.Flame;
import uet.oop.Bomberman.entities.character.enemy.Enemy;
import uet.oop.Bomberman.entities.tile.Wall;
import uet.oop.Bomberman.entities.tile.item.Item;
import uet.oop.Bomberman.graphics.Screen;
import uet.oop.Bomberman.graphics.Sprite;
import uet.oop.Bomberman.input.Keyboard;
import uet.oop.Bomberman.level.Coordinates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
    public static List<Item> _items = new ArrayList<>();

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        if(_input.space && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {

            int xt = Coordinates.pixelToTile(_x + _sprite.getSize()/2);
            int yt = Coordinates.pixelToTile(_y + _sprite.getSize()/2 - _sprite.getSize());

            placeBomb(xt, yt);
            Sound.getIstance().getAudio(Sound.BOMB).play();
            Game.addBombRate(-1);

            _timeBetweenPutBombs = 30;


        }
    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x, y, _board);
        _board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        Sound.getIstance().getAudio(Sound.BOMBER_DIE).play();
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        int xa =0, ya =0;
        if(_input.up)   ya--;
        if(_input.down) ya++;
        if(_input.left) xa--;
        if(_input.right) xa++;

        if(xa != 0 || ya != 0){
            move(xa*Game.getBomberSpeed(), ya*Game.getBomberSpeed());
            _moving =true;
        }
        else    _moving = false;
    }

    @Override
    public boolean canMove(double x, double y) {
        // @todo: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        int tileX = Coordinates.pixelToTile(x);
        int tileY = Coordinates.pixelToTile(y);
//        System.out.println(tileX + " " + tileY);
        Entity nextEntity = _board.getEntity(tileX, tileY, this);
        return collide(nextEntity);
    }

    public void moveCenterX() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.getRealWidth() / 2;
        int tileCenterX = Coordinates.pixelToTile(centerX);
        _x = Coordinates.tileToPixel(tileCenterX) + pixelOfEntity / 2 - _sprite.getRealWidth() / 2;

        System.out.println(pixelOfEntity + " " + centerX + " " + tileCenterX + " " + _x + " " + _sprite.getRealWidth());
    }

    public void moveCenterY() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerY = _y - _sprite.getRealHeight() / 2;
        int tileCenterY = Coordinates.pixelToTile(centerY);
        _y = Coordinates.tileToPixel(tileCenterY) + pixelOfEntity / 2 + _sprite.getRealHeight() / 2;
    }

    public void autoMoveCenter() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.getRealWidth() / 2;
        double centerY = _y - _sprite.getRealHeight() / 2;

        boolean contactTop = !canMove(centerX, centerY - pixelOfEntity / 2);
        boolean contactDown = !canMove(centerX, centerY + pixelOfEntity / 2);
        boolean contactLeft = !canMove(centerX - pixelOfEntity / 2, centerY);
        boolean contactRight = !canMove(centerX + pixelOfEntity / 2, centerY);

        // Các trường hợp đi một nửa người vào tường cũng tự động căn giữa.
        if (_direction != 0 && contactDown) moveCenterY();
        if (_direction != 1 && contactLeft) moveCenterX();
        if (_direction != 2 && contactTop) moveCenterY();
        if (_direction != 3 && contactRight) moveCenterX();
    }

    @Override
    public void move(double xa, double ya) {
        // @todo: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // @todo: nhớ cập nhật giá trị _direction sau khi di chuyển : up, right, down, left -> 0, 1, 2, 3
        // @todo: Di chuyển nhân vật ra giữa.

        // Tính tọa độ tâm người
        double centerX = _x + _sprite.getRealWidth() / 2;
        double centerY = _y - _sprite.getRealHeight() / 2;
       // System.out.println(_x + " " + _y);

        if (xa > 0) _direction = 1;
        if (xa < 0) _direction = 3;
        if (ya > 0) _direction = 2;
        if (ya < 0) _direction = 0;
        if (canMove(centerX + xa, centerY + ya)) {
            _x += xa;
            _y += ya;
        }

        autoMoveCenter();
    }

    public boolean handleCollidePortal() {
        if (_board.detectNoEnemies()) {
            Sound.getIstance().getAudio(Sound.WIN).play();
            _board.nextLevel();
            return true;
        }

        return false;
    }

    @Override
    public boolean collide(Entity e) {
        // @todo: xử lý va chạm với Flame
        // @todo: xử lý va chạm với Enemy
        if (e instanceof Flame) {
            this.kill();
            return true;
        }

        if (e instanceof Enemy) {
            this.kill();
            return true;
        }

        if (e instanceof Wall) return false;

        if (e instanceof LayeredEntity) return e.collide(this);

        return true;

    }


    public void addItem(Item i) {
        if(i.isRemoved())   return;

        _items.add(i);

        i.setValues();
    }


    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}

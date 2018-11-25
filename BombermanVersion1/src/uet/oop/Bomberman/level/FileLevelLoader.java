package uet.oop.Bomberman.level;

		import uet.oop.Bomberman.Board;
		import uet.oop.Bomberman.Game;
		import uet.oop.Bomberman.entities.LayeredEntity;
		import uet.oop.Bomberman.entities.character.Bomber;
		import uet.oop.Bomberman.entities.character.enemy.Balloon;
		import uet.oop.Bomberman.entities.character.enemy.Doll;
		import uet.oop.Bomberman.entities.character.enemy.Kondoria;
		import uet.oop.Bomberman.entities.character.enemy.Oneal;
		import uet.oop.Bomberman.entities.tile.Grass;
		import uet.oop.Bomberman.entities.tile.Portal;
		import uet.oop.Bomberman.entities.tile.Wall;
		import uet.oop.Bomberman.entities.tile.destroyable.Brick;
		import uet.oop.Bomberman.entities.tile.item.BombItem;
		import uet.oop.Bomberman.entities.tile.item.FlameItem;
		import uet.oop.Bomberman.entities.tile.item.SpeedItem;
		import uet.oop.Bomberman.exceptions.LoadLevelException;
		import uet.oop.Bomberman.graphics.Screen;
		import uet.oop.Bomberman.graphics.Sprite;

		import java.io.BufferedReader;
		import java.io.IOException;
		import java.io.InputStream;
		import java.io.InputStreamReader;
		import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;

	public FileLevelLoader(Board board, int level) throws LoadLevelException{
		super(board, level);
	}

	@Override
	public void loadLevel(int level) throws LoadLevelException {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		InputStream inputStream = getClass().getResourceAsStream("/levels/Level" + Integer.toString(level) + ".txt");
		BufferedReader in = new BufferedReader( new InputStreamReader(inputStream));

		String line = null;
		try {
			line = in.readLine();

			StringTokenizer tokens = new StringTokenizer(line);

			_level = Integer.parseInt(tokens.nextToken());
			_height = Integer.parseInt(tokens.nextToken());
			_width = Integer.parseInt(tokens.nextToken());

			_map = new char[_height][_width];

			for(int i=0; i<_height; i++) {
				line = in.readLine();
				char[] tmp = line.toCharArray();
				for (int j = 0; j<_width; j++)
					_map[i][j] = tmp[j];
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình

		for(int i=0; i<getHeight(); i++) {
			for(int j=0; j<getWidth(); j++) {
				//int pos = i + j * _width;
				switch (_map[i][j]) {
					case '#' :
						int x = j, y =i;
						_board.addEntity(x + y * _width , new Wall(x, y, Sprite.wall ));
						break;
					case '*' :
						int xB = j, yB = i;
						_board.addEntity(xB + yB * _width,
								new LayeredEntity(xB, yB,
										new Grass(xB, yB, Sprite.grass),
										new Brick(xB, yB, Sprite.brick)
								)
						);
						break;
					case 'x' :
						int xP = j, yP = i;
						_board.addEntity(xP + yP * _width,
								new LayeredEntity(xP, yP,
										new Grass(xP ,yP, Sprite.grass),
										new Portal(xP, yP, Sprite.portal),
										new Brick(xP, yP, Sprite.brick)
								)
						);
						break;
					case 'p' :
						int xBomber = j, yBomber = i;
						_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
						Screen.setOffset(0, 0);
						_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
						break;
					case '1' :
						int xE = j, yE = i;
						_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
						_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
						break;
					case '2' :
						int xO = j, yO = i;
						_board.addCharacter( new Oneal(Coordinates.tileToPixel(xO), Coordinates.tileToPixel(yO) + Game.TILES_SIZE, _board));
						_board.addEntity(xO + yO * _width, new Grass(xO, yO, Sprite.grass));
						break;
					case '3' :
						int xK = j, yK = i;
						_board.addCharacter(new Kondoria(Coordinates.tileToPixel(xK), Coordinates.tileToPixel(yK) + Game.TILES_SIZE, _board));
						_board.addEntity(xK + yK * _width, new Grass(xK, yK, Sprite.grass));
						break;
					case '4' :
						int xD = j, yD = i;
						_board.addCharacter(new Doll(Coordinates.tileToPixel(xD), Coordinates.tileToPixel(yD) + Game.TILES_SIZE, _board));
						_board.addEntity(xD + yD * _width, new Grass(xD, yD, Sprite.grass));
						break;
					case 'b' :
						int xBI = j, yBI = i;
						_board.addEntity(xBI + yBI * _width,
								new LayeredEntity(xBI, yBI,
										new Grass(xBI ,yBI, Sprite.grass),
										new BombItem(xBI, yBI, Sprite.powerup_bombs),
										new Brick(xBI, yBI, Sprite.brick)
								)
						);
						break;
					case 'f' :
						int xFI = j, yFI = i;
						_board.addEntity(xFI + yFI * _width,
								new LayeredEntity(xFI, yFI,
										new Grass(xFI ,yFI, Sprite.grass),
										new FlameItem(xFI, yFI, Sprite.powerup_flames),
										new Brick(xFI, yFI, Sprite.brick)
								)
						);
						break;
					case 's' :
						int xI = j, yI = i;
						_board.addEntity(xI + yI * _width,
								new LayeredEntity(xI, yI,
										new Grass(xI ,yI, Sprite.grass),
										new SpeedItem(xI, yI, Sprite.powerup_speed),
										new Brick(xI, yI, Sprite.brick)
								)
						);
						break;
					default :
						int xG = j, yG =i;
						_board.addEntity(xG + yG * _width, new Grass(xG, yG, Sprite.grass ));
						break;


				}
			}
		}


	}

}

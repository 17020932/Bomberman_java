package uet.oop.Bomberman.entities.tile.item;

import uet.oop.Bomberman.entities.tile.Tile;
import uet.oop.Bomberman.graphics.Sprite;

public abstract class Item extends Tile {

	protected boolean _active = false;

	public Item(int x, int y, Sprite sprite)
	{
		super(x, y, sprite);
	}

	public abstract void setValues();

}

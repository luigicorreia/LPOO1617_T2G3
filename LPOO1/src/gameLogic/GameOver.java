package gameLogic;

public class GameOver {
	private Hero hero;
	private Enemy guard;
	private Orde orde;
	private MapGame map;

	
	public GameOver(Hero hero, Enemy guard, MapGame map){
		this.hero=hero;
		this.guard=guard;
		this.map = map;
		
	}
	
	public GameOver(Hero hero, Orde orde, MapGame map)
	{
		this.hero = hero;
		this.orde = orde;
		this.map = map;
	}
	
	
	public boolean getGameOver(MapGame map) 
	{
		if(map instanceof Mapa1)
		{
			if(guard instanceof Drunken && guard.isSleeping()){return false;}
			else if((this.hero.getHi() == guard.getI()+1 && this.hero.getHj() == this.guard.getJ()) || (this.hero.getHi()== guard.getI()-1) && (this.hero.getHj() == this.guard.getJ()) || (this.hero.getHi() == this.guard.getI() && (this.hero.getHj() == this.guard.getJ()+1 ||this.hero.getHj() == this.guard.getJ()-1 ) || (this.hero.getHi() == this.guard.getI() && this.hero.getHj() == this.guard.getJ()))){
				map.setRunning(false);return true;} 
			else{return false;}
		}
		else if(map instanceof Mapa2) 
		{
			
			for(int k = 0; k < this.orde.getOrde().size(); k++)
			{
				
				int wi = this.orde.getOrde().get(k).getWeaponI();
				int wj = this.orde.getOrde().get(k).getWeaponJ();
				
				
				
				if((this.hero.getHi() == wi && this.hero.getHj() == wj) || (this.hero.getHi() == wi-1 && this.hero.getHj() == wj) || (this.hero.getHi() == wi+1 && this.hero.getHj() == wj) || (this.hero.getHi() == wi && this.hero.getHj() == wj-1) || (this.hero.getHi() == wi && this.hero.getHj() == wj+1))
				{map.setRunning(false);return true;}
				}
		}
		else if(map instanceof NewMapGame){
		{
			if(map.isHasGuard())
			{
				if(guard instanceof Drunken && guard.isSleeping()){return false;}
				else if((this.hero.getHi() == guard.getI()+1 && this.hero.getHj() == this.guard.getJ()) || (this.hero.getHi()== guard.getI()-1) && (this.hero.getHj() == this.guard.getJ()) || (this.hero.getHi() == this.guard.getI() && (this.hero.getHj() == this.guard.getJ()+1 ||this.hero.getHj() == this.guard.getJ()-1 ) || (this.hero.getHi() == this.guard.getI() && this.hero.getHj() == this.guard.getJ()))){
					map.setRunning(false);return true;} 
				else{return false;}
			}
			else if(map.isHasOgre())
			{
				for(int k = 0; k < this.orde.getOrde().size(); k++)
				{
					int oi = this.orde.getOrde().get(k).getI();
					int oj = this.orde.getOrde().get(k).getJ();
					int wi = this.orde.getOrde().get(k).getWeaponI();
					int wj = this.orde.getOrde().get(k).getWeaponJ();
					
					if((this.map.getHero().getHi() == oi && this.map.getHero().getHj() == oj) || (this.map.getHero().getHi() == oi-1 && this.map.getHero().getHj() == oj) || (this.map.getHero().getHi() == oi+1 && this.map.getHero().getHj() == oj) || (this.map.getHero().getHi() == oi && this.map.getHero().getHj() == oj+1) || (this.map.getHero().getHi() == oi && this.map.getHero().getHj() == oj-1))
					{map.setRunning(false);return true;}
					else if((this.hero.getHi() == wi && this.hero.getHj() == wj) || (this.hero.getHi() == wi-1 && this.hero.getHj() == wj) || (this.hero.getHi() == wi+1 && this.hero.getHj() == wj) || (this.hero.getHi() == wi && this.hero.getHj() == wj-1) || (this.hero.getHi() == wi && this.hero.getHj() == wj+1))

					{map.setRunning(false);return true;}
				}
				
			}
		}
		}
		return false;
	}
	
	
	

}

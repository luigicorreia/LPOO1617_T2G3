package gameLogic;

public abstract class Character {
	
	
	
	
	//so esta feito para o Hero. Falta acrescentar para o guarda e ogre.Diferen�a � que para o guarda temos que acrescentar a condi�ao do hero (apenas fazer um "ou" na linha 19) e para o ogre al�m do ou h� que tratar do $ quando vai para cima da chave)
	public boolean move(MapGame map, int di, int dj){
		boolean gameOver = false;
		boolean victory = false;
		if(map instanceof Mapa1 ){
			
			if(map.getMap()[di][dj] == 'X' ){
				return false;
				
			} else if(map.getMap()[di][dj] == 'I'){
				return false;
			}else if(map.getMap()[di][dj] == 'G' || map.getMap()[di][dj] == 'H'){
				gameOver = true;
				return gameOver;
			}else if(map.getMap()[di][dj] == 'S'){
				victory = true;
				return victory;
			}else if(map.getMap()[di][dj] == 'K'){ 
				((Mapa1) map).setDoors();
			    return true;
			}else if(map.getMap()[di][dj] == ' '){
				return true;
			}
		} else if(map instanceof Mapa2){
			
			if(map.getMap()[di][dj] == 'X'){
				return false;
			} else if(map.getMap()[di][dj] == 'I'){
				return false;
			}else if(map.getMap()[di][dj] == '0' || map.getMap()[di][dj] == '*' || map.getMap()[di][dj] == 'H' ){
				gameOver = true;
				return gameOver;
			}
			
		}
		
		
		
		
		
		return false;};
	
	
}

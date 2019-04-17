package strtmpp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ArticleMatch {

	class DataType {
		int distance;
		int prevX;
		int prevY;
		public DataType(int distance, int prevX, int prevY) {
			this.distance = distance;
			this.prevX = prevX;
			this.prevY = prevY;
		}
		public int getDistance() {
			return distance;
		}
		public int getPrevX() {
			return prevX;
		}
		public int getPrevY() {
			return prevY;
		}
		public void setDistance(int distance) {
			this.distance = distance;
		}
		public void setPrevXY(int prevX, int prevY) {
			this.prevX = prevX;
			this.prevY = prevY;
		}
	}
	
	public void getMatch(String origin, String typed) {
		int lenOrigin = origin.length();
		int lenTyped = typed.length();
		DataType[][] data = new DataType[lenOrigin + 1][lenTyped + 1];
		data[0][0] = new DataType(0, -1, -1); // previous (-1, -1) means start point
		for (int j = 1; j < lenTyped + 1; j++) {
			data[0][j] = new DataType(j, 0, j - 1); // origin empty, type is number of differences
		}
		for (int i = 1; i < lenOrigin + 1; i++) {
			data[i][0] = new DataType(i, i - 1, 0); // type is empty, origin is number of differences 
			for (int j = 1; j < lenTyped + 1; j++) {
				int m = i - 1;
				int n = j - 1;
				if (origin.charAt(m) == typed.charAt(n)) {
					data[i][j] = new DataType(data[i - 1][j - 1].getDistance(), i - 1, j - 1);
				} else {
					int distanceLess = data[i - 1][j].getDistance(); // if typed less
					int distanceWrong = data[i - 1][j - 1].getDistance(); // if typed wrong
					int distanceMore = data[i][j - 1].getDistance(); // if typed more
					if (distanceWrong <= distanceLess && distanceWrong <= distanceMore) {
						data[i][j] = new DataType(distanceWrong + 1, i - 1, j - 1);
					} else if (distanceLess <= distanceWrong && distanceLess <= distanceMore) {
						data[i][j] = new DataType(distanceLess + 1, i - 1, j);
					} else {
						data[i][j] = new DataType(distanceMore + 1, i, j - 1);
					}
				}
			}
		}
//		int destX = lenOrigin - 1;
//		int destY = lenTyped - 1;
		HashMap<Integer,ArrayList<String>> lesssign =  new HashMap<Integer,ArrayList<String>>();
		
		int curX = lenOrigin;
		int curY = lenTyped;
		int[] resOrigin = new int[lenOrigin];
		int[] resTyped = new int[lenTyped];
		while(curX != 0 || curY != 0) {
			int prevX = data[curX][curY].getPrevX();
			int prevY = data[curX][curY].getPrevY();
			if (data[prevX][prevY].getDistance() == data[curX][curY].getDistance() - 1) {
				if (prevX < curX && prevY < curY) {
					resOrigin[curX - 1] = 2;
					resTyped[curY - 1] = 2;
				} else if (prevX < curX) {
					resOrigin[curX - 1] = 1;
					if(lesssign.containsKey(curY-1))
						lesssign.get(curY-1).add(String.valueOf(origin.charAt(curX-1)));
					else{
						ArrayList<String> temp = new ArrayList<String>();
						temp.add(String.valueOf(origin.charAt(curX-1)));
						lesssign.put(curY-1,temp);
					}
				} else {
					resTyped[curY - 1] = 1;
					
				}
			}
			curX = prevX;
			curY = prevY;
		}
		System.out.println(origin);
		System.out.println(Arrays.toString(resOrigin));
		System.out.println(typed);
		System.out.println(Arrays.toString(resTyped));
		for(Integer key:lesssign.keySet()){
			String temp = ""+key;
			for(int i = 0;i<lesssign.get(key).size();i++)
				temp+=":"+lesssign.get(key).get(i);
			System.out.println(temp);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArticleMatch am = new ArticleMatch();
		String origin = "小心而漫长；拆除的时候却只需轻轻一拉。人正是因为有了不能忘记的回忆才会坚强";
		String typed = "小心而漫；拆除的时候却只轻一拉。人正是因为有了不能忘记的会议才会坚强";
		am.getMatch(origin, typed);
	}

}

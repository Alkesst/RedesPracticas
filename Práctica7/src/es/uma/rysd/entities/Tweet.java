package es.uma.rysd.entities;

public class Tweet {
	public String created_at;
	public String id_str;
	public String text;
	public User user;
	public Integer retweet_count;
	public Integer favorite_count;

	public String toString(){
		return "\""+text+"\" by "+user.name + " (rt: "+retweet_count + " fav: "+favorite_count +")";
	}
}

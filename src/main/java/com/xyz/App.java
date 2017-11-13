package com.xyz;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;


public class Parser {
    public static void main(String[] args) throws IOException {
        parse_reviews("https://hard.rozetka.com.ua/ua/computers/c80095/filter/");
    }

    public static void parse_reviews(String url)throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements items = doc.getElementsByAttributeValue("class", "g-rating");
        for(Element item : items) {
            String n_url = item.child(0).attr("href");
            String n_rev = item.child(0).attr("data-count");
            if(n_rev.equals("")){
                n_rev = "0";
            }
            String result = n_rev + " reviews from " + n_url + " \n";
            System.out.println(result);
            parse_star_reviews(n_url);
        }
    }

    public static void parse_star_reviews(String url)throws IOException{
        Document document = Jsoup.connect(url).get();
        Elements items = document.getElementsByAttributeValue("class", "sprite g-rating-stars");
        int  count = 0;
        for(Element item : items){
            int n_rev = Integer.parseInt(item.child(0).attr("content"));
            count += n_rev;
        }
        String result = count + " star_reviews from " + url + " \n";
        System.out.print(result);
        file_saver(result);

    }

    public static void file_saver(String result){
        File file = new File("parsing_reviews_results.csv");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.write(result);
            writer.flush();
            writer.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

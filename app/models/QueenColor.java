package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import controllers.Colonies;

public class QueenColor {
  @Id
  public Integer number;
  public String name;
  
  public QueenColor(Integer number, String name) {
    this.number = number;
    this.name = name;
  }
  
  public static List<QueenColor> getColors() {
    List<QueenColor> colors = new ArrayList<>();
    
    colors.add(new QueenColor(Colonies.COLOR_BLUE, "Blau"));
    colors.add(new QueenColor(Colonies.COLOR_WHITE, "Weiß"));
    colors.add(new QueenColor(Colonies.COLOR_YELLOW, "Gelb"));
    colors.add(new QueenColor(Colonies.COLOR_RED, "Rot"));
    colors.add(new QueenColor(Colonies.COLOR_GREEN, "Grün"));
    
    return colors;
  }

  @Override
  public String toString() {
    return name;
  }
}
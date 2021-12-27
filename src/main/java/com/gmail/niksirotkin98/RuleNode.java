package com.gmail.niksirotkin98;

import java.util.HashMap;
import java.util.Map;

public class RuleNode {

    private String key;
    
    private int position;
    
    private Map<Integer, RuleNode> loserNodes;
    
    RuleNode(String key, int position){
        this.key = key;
        this.position = position;
        this.loserNodes = new HashMap<Integer, RuleNode>();
    }
    
    public String getKey() {
        return key;
    }
    
    public int getPosition() {
        return position;
    }
    
    public RuleNode addLoserNode(RuleNode n) {
        loserNodes.put(n.position, n);
        return this;
    }
    
    public RuleNode getLoser(int loserPosition) {
        return loserNodes.get(loserPosition);
    }
    
    @Override
    public String toString() {
        String out = "Rule: " + key + ", wins:";
        for (RuleNode n : loserNodes.values()) {
            out += " " + n.key;
        }
        return out;
    }
    
}

package com.gmail.niksirotkin98;

public class Controller {
    
    private RuleNode[] rules;
    
    Controller(String[] rules){
        this.rules = new RuleNode[rules.length];
        addRules( rules );
        determineLosers();
    }
    
    private void addRules(String[] rules) {
        for (int i = 0; i < rules.length; i++)
        {
            this.rules[i] = new RuleNode(rules[i], i);
        }
    }
    
    private void determineLosers() {
        int half = (this.rules.length - 1) / 2; 
        for (RuleNode r : this.rules)
        {
            int p = r.getPosition() - 1 < 0? rules.length - 1: r.getPosition() - 1;
            for (int i = 0; i < half; i++, p = p - 1 < 0? rules.length - 1: p - 1)
            {
                r.addLoserNode(this.rules[p]);
            }
        }	
    }
    
    public int getRulesCount() {
        return rules.length;
    }
    
    public RuleNode getRule(int position) {
        if (isBetween( position, -1, rules.length ))
        {
            return rules[position];
        }
        return null;
    }

    public String getRuleKey(int position){
        RuleNode rule = getRule(position);
        if( rule != null ){
            return rule.getKey();
        }
        return null;
    }
    
    public int computeResult(int rule1, int rule2){
        if ( isBetween( rule1, -1, rules.length ) && isBetween( rule2, -1, rules.length )) 
        {
            if (rule1 == rule2)
            {
                return 0; //Draft
            }
            else if (rules[rule1].getLoser(rule2) == null)
            {
                return -1; //rule2 win
            }
            else
            {
                return 1; //rule1 win
            }
        } 
        return 999; //error: rule not found
    }

    public static boolean isBetween(int x, int lower, int upper){
        return (x > lower) && (x < upper);
    }
}

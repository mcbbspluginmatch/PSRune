package me.wuxie.psrune.gui;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.psrune.rune.Rune;

import java.util.Map;

public class InvRuneData {
    @Getter
    private Map<Integer, Rune> runedata;
    @Getter
    @Setter
    private String data;
    @Getter
    private int loreline;
    public InvRuneData(int loreline,Map<Integer, Rune> runedata,String data){
        this.runedata = runedata;
        this.data = data;
        this.loreline = loreline;
    }
}

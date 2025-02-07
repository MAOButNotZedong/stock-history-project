package org.example.finalproject.dto;

public record MutatedList<L, B>(L list, B isChanged) {
    public  MutatedList(L list, B isChanged)  {
        this.list = list;
        this.isChanged = isChanged;
    }
}
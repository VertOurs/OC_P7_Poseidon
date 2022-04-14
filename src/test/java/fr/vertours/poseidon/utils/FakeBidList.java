package fr.vertours.poseidon.utils;

import fr.vertours.poseidon.domain.BidList;
import fr.vertours.poseidon.dto.BidListDTO;

import java.util.ArrayList;
import java.util.List;

public class FakeBidList {

    public static BidList getEntity1() {
        BidList bidList = new BidList();
        bidList.setAccount("Account");
        bidList.setBidQuantity(5D);
        bidList.setBidListId(1);
        bidList.setType("Type");

        return bidList;
    }
    public static BidListDTO getDTO1() {
        BidListDTO dto = new BidListDTO();
        dto.setAccount("Account");
        dto.setBidQuantity(5D);
        dto.setId(1);
        dto.setType("Type");
        return dto;
    }
    public static List<BidList> getAll() {
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(getEntity1());
        return bidLists;
    }
}

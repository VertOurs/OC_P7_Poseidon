package fr.vertours.poseidon.utils;

import fr.vertours.poseidon.domain.Trade;
import fr.vertours.poseidon.dto.TradeDTO;

import java.util.ArrayList;
import java.util.List;

public class FakeTrade {

    public static Trade getEntity1() {
        Trade trade = new Trade();
        trade.setAccount("Account");
        trade.setBuyQuantity(2D);
        trade.setType("Type");
        return trade;
    }

    public static TradeDTO getDTO1() {
        TradeDTO dto = new TradeDTO();
        dto.setAccount("Account");
        dto.setBuyQuantity(2D);
        dto.setType("Type");
        return dto;
    }
    public static List<Trade> getAll() {
        List<Trade> trades = new ArrayList<>();
        trades.add(getEntity1());
        return trades;
    }
}

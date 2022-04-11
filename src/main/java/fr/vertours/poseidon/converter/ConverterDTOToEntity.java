package fr.vertours.poseidon.converter;

import fr.vertours.poseidon.domain.*;
import fr.vertours.poseidon.dto.*;

public class ConverterDTOToEntity {

    public static BidList getBidListEntity(BidListDTO dto) {
        BidList bidList = new BidList();
        bidList.setAccount(dto.getAccount());
        bidList.setType(dto.getType());
        bidList.setBidQuantity(dto.getBidQuantity());

        return bidList;
    }

    public static CurvePoint getCurvePointEntity(CurvePointDTO dto) {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(dto.getCurveId());
        curvePoint.setTerm(dto.getTerm());
        curvePoint.setValue(dto.getValue());

        return curvePoint;
    }

    public static Rating getRatingEntity(RatingDTO dto) {
        Rating rating = new Rating();
        rating.setMoodysRating(dto.getMoodysRating());
        rating.setSandPRating(dto.getSandPRating());
        rating.setFitchRating(dto.getFitchRating());
        rating.setOrderNumber(dto.getOrderNumber());
        return rating;
    }

    public static RuleName getRuleNameEntity(RuleNameDTO dto) {
        RuleName ruleName = new RuleName();
        ruleName.setName(dto.getName());
        ruleName.setDescription(dto.getDescription());
        ruleName.setJson(dto.getJson());
        ruleName.setTemplate(dto.getTemplate());
        ruleName.setSqlStr(dto.getSqlStr());
        ruleName.setSqlPart(dto.getSqlPart());
        return ruleName;
    }
    public static Trade getTradeEntity(TradeDTO dto) {
        Trade trade = new Trade();
        trade.setAccount(dto.getAccount());
        trade.setType(dto.getType());
        trade.setBuyQuantity(dto.getBuyQuantity());
        return trade;
    }
}

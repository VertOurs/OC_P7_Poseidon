package fr.vertours.poseidon.converter;

import fr.vertours.poseidon.domain.*;
import fr.vertours.poseidon.dto.*;


public class ConverterEntityToDTO {

    public static BidListDTO getBidListDTO(BidList bean) {
        return new BidListDTO(
                bean.getBidListId(),
                bean.getAccount(),
                bean.getType(),
                bean.getBidQuantity());
    }

    public static CurvePointDTO getCurvePointDTO(CurvePoint bean) {
        return new CurvePointDTO(
                bean.getCurveId(),
                bean.getTerm(),
                bean.getValue());
    }
    public static RatingDTO getRatingDTO(Rating bean) {
        return new RatingDTO(
                bean.getMoodysRating(),
                bean.getSandPRating(),
                bean.getFitchRating(),
                bean.getOrderNumber()
        );
    }

    public static RuleNameDTO getRuleNameDTO(RuleName bean) {
        return new RuleNameDTO(
                bean.getName(),
                bean.getDescription(),
                bean.getJson(),
                bean.getTemplate(),
                bean.getSqlStr(),
                bean.getSqlPart()
        );
    }
    public static TradeDTO getTradeDTO(Trade bean) {
        return new TradeDTO(
                bean.getAccount(),
                bean.getType(),
                bean.getBuyQuantity()
        );
    }
    public static UserDTO getUserDTO(User bean) {
        return new UserDTO(
                bean.getUsername(),
                bean.getPassword(),
                bean.getFullname(),
                bean.getRole()
        );
    }
}

package fr.vertours.poseidon.utils;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.dto.CurvePointDTO;

import java.util.ArrayList;
import java.util.List;
public class FakeCurvePoint {

    public static CurvePoint getEntity1() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(2D);
        curvePoint.setValue(3D);
        return curvePoint;
    }
    public static CurvePointDTO getDTO1() {
        CurvePointDTO dto = new CurvePointDTO();
        dto.setCurveId(1);
        dto.setTerm(2D);
        dto.setValue(3D);
        return dto;
    }
    public static List<CurvePoint> getAll() {
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(getEntity1());
        return curvePoints;
    }
}

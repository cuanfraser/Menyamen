package org.menyamen.snarl.util;

import java.awt.Point;
import org.json.JSONArray;

public final class Util {

    private Util() {
    }

    /**
     * Convert Point to (point) JSON
     * 
     * @param point
     * @return
     */
    public static JSONArray toRowCol(Point point) {
        JSONArray converted = new JSONArray();
        converted.put(point.y);
        converted.put(point.x);
        return converted;
    }

    /**
     * Convert (point) JSON to Point
     * 
     * @param rowCol
     * @return
     */
    public static Point fromRowCol(JSONArray rowCol) {
        int row = rowCol.getInt(0);
        int col = rowCol.getInt(1);
        Point converted = new Point(col, row);
        return converted;
    }
}
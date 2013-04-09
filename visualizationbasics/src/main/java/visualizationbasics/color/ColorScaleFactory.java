/* ***** BEGIN LICENSE BLOCK *****
 *
 * Copyright (c) 2005-2007 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * This file is part of Projection Explorer (PEx).
 *
 * How to cite this work:
 *
@inproceedings{paulovich2007pex,
author = {Fernando V. Paulovich and Maria Cristina F. Oliveira and Rosane
Minghim},
title = {The Projection Explorer: A Flexible Tool for Projection-based
Multidimensional Visualization},
booktitle = {SIBGRAPI '07: Proceedings of the XX Brazilian Symposium on
Computer Graphics and Image Processing (SIBGRAPI 2007)},
year = {2007},
isbn = {0-7695-2996-8},
pages = {27--34},
doi = {http://dx.doi.org/10.1109/SIBGRAPI.2007.39},
publisher = {IEEE Computer Society},
address = {Washington, DC, USA},
}
 *
 * PEx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * PEx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * This code was developed by members of Computer Graphics and Image
 * Processing Group (http://www.lcad.icmc.usp.br) at Instituto de Ciencias
 * Matematicas e de Computacao - ICMC - (http://www.icmc.usp.br) of
 * Universidade de Sao Paulo, Sao Carlos/SP, Brazil. The initial developer
 * of the original code is Fernando Vieira Paulovich <fpaulovich@gmail.com>.
 *
 * Contributor(s): Rosane Minghim <rminghim@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along
 * with PEx. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */
package visualizationbasics.color;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ColorScaleFactory {

    public enum ColorScaleType {

        HEATED_OBJECTS("Heated objects scale"),
        GRAY_SCALE("Gray scale"),
        LINEAR_GRAY_SCALE("Linear gray scale"),
        LOCS_SCALE("Linear Optminal Color Scale (LOCS)"),
        RAINBOW_SCALE("Rainbow scale"),
        PSEUDO_RAINBOW_SCALE("Pseudo-rainbow scale"),
        DYNAMIC_SCALE("Dynamic scale"),
        CATEGORY_SCALE("Category scale"),
        BLUE_TO_YELLOW_SCALE("Blue to yellow scale"),
        BLUE_TO_CYAN("Blue to cyan scale"),
        GREEN_TO_WHITE_SCALE("Green to white scale"),
        ORANGE_TO_BLUESKY("Orange to blue sky scale"),
        BLUESKY_TO_ORANGE_SCALE("Blue sky to orange scale");

        private ColorScaleType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        private final String name;
    }

    public static ColorScale getInstance(ColorScaleType type) {

        if (type == ColorScaleType.HEATED_OBJECTS) {
            return new HeatedObjectScale();
        } else if (type == ColorScaleType.GRAY_SCALE) {
            return new GrayScale();
        } else if (type == ColorScaleType.LINEAR_GRAY_SCALE) {
            return new LinearGrayScale();
        } else if (type == ColorScaleType.LOCS_SCALE) {
            return new LocsScale();
        } else if (type == ColorScaleType.RAINBOW_SCALE) {
            return new RainbowScale();
        } else if (type == ColorScaleType.PSEUDO_RAINBOW_SCALE) {
            return new PseudoRainbowScale();
        } else if (type == ColorScaleType.CATEGORY_SCALE) {
            return new CategoryScale();
        } else if (type == ColorScaleType.BLUE_TO_YELLOW_SCALE) {
            return new BlueToYellowScale();
        } else if (type == ColorScaleType.BLUE_TO_CYAN) {
            return new BlueToCyanScale();
        } else if (type == ColorScaleType.GREEN_TO_WHITE_SCALE) {
            return new GreenToWhiteScale();
        } else if (type == ColorScaleType.DYNAMIC_SCALE) {
            return new DynamicScale();
        } else if (type == ColorScaleType.BLUESKY_TO_ORANGE_SCALE) {
            return new BlueSkyToOrange();
        } else if (type == ColorScaleType.ORANGE_TO_BLUESKY) {
            return new OrangeToBlueSky();
        }

        return null;
    }
}

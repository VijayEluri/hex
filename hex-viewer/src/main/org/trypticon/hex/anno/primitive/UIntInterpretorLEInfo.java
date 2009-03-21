/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009  Trejkaz, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.trypticon.hex.anno.primitive;

import java.util.List;
import java.util.Collections;
import java.util.Map;

import org.trypticon.hex.anno.InterpretorInfo;
import org.trypticon.hex.anno.Interpretor;

/**
 * Info for {@link org.trypticon.hex.anno.primitive.UIntInterpretorLE}.
 *
 * @author trejkaz
 */
public class UIntInterpretorLEInfo implements InterpretorInfo {

    public String getHumanName() {
        return "Unsigned 32-bit Integer (Little Endian)";
    }

    public List<Option> getOptions() {
        return Collections.emptyList();
    }

    public Interpretor create(Map<String, Object> options) {
        return new UIntInterpretorLE();
    }
}
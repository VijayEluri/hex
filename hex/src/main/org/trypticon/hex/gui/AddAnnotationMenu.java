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

package org.trypticon.hex.gui;

import javax.swing.JMenu;

import org.trypticon.hex.anno.primitive.PrimitiveInterpretorStorage;
import org.trypticon.hex.anno.InterpretorInfo;

/**
 * With with the list of annotations which can be added by the user.
 *
 * @author trejkaz
 */
public class AddAnnotationMenu extends JMenu {
    public AddAnnotationMenu() {
        super("Add Annotation");

        // TODO: This should go through the MasterInterpreterStorage once we have categories.

        add(buildPrimitivesMenu());
    }

    private JMenu buildPrimitivesMenu() {
        JMenu menu = new JMenu("Primitive");
        for (InterpretorInfo info : new PrimitiveInterpretorStorage().getInterpretorInfos()) {
            menu.add(new AddAnnotationAction(info));
        }
        return menu;
    }


}

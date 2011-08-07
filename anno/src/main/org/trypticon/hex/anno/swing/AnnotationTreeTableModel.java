/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2010  Trejkaz, Hex Project
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

package org.trypticon.hex.anno.swing;

import org.trypticon.hex.anno.Annotation;
import org.trypticon.hex.anno.AnnotationCollection;
import org.trypticon.hex.anno.AnnotationCollectionEvent;
import org.trypticon.hex.anno.AnnotationCollectionListener;
import org.trypticon.hex.anno.GroupAnnotation;
import org.trypticon.hex.anno.MutableAnnotation;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.util.swingxsupport.AbstractTreeTableModel;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

/**
 * Tree model for annotations.
 *
 * @author trejkaz
 */
public class AnnotationTreeTableModel extends AbstractTreeTableModel implements AnnotationCollectionListener {
    static final int TYPE_COLUMN = 0;
    static final int VALUE_COLUMN = 1;
    static final int NOTE_COLUMN = 2;
    private static final int COLUMN_COUNT = 3;

    private static final String[] COLUMN_NAMES = { "Type", "Value", "Notes" }; // TODO: Localise

    private final AnnotationCollection annotations;
    private final Binary binary;

    public AnnotationTreeTableModel(AnnotationCollection annotations, Binary binary) {
        this.annotations = annotations;
        this.binary = binary;

        annotations.addAnnotationCollectionListener(this);
    }

    public AnnotationCollection getAnnotations() {
        return annotations;
    }

    public Object getRoot() {
        return annotations.getRootGroup();
    }

    public boolean isLeaf(Object o) {
        return !(o instanceof GroupAnnotation);
    }

    public int getChildCount(Object node) {
        if (node instanceof GroupAnnotation) {
            return ((GroupAnnotation) node).getAnnotations().size();
        } else {
            return 0;
        }
    }

    public Object getChild(Object node, int index) {
        if (node instanceof GroupAnnotation) {
            return ((GroupAnnotation) node).getAnnotations().get(index);
        } else {
            return null;
        }
    }

    public int getIndexOfChild(Object node, Object child) {
        if (node instanceof GroupAnnotation) {
            //noinspection SuspiciousMethodCalls
            return ((GroupAnnotation) node).getAnnotations().indexOf(child);
        } else {
            return -1;
        }
    }

    public void valueForPathChanged(TreePath treePath, Object o) {
        // I don't think I will need this.
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    public int getHierarchicalColumn() {
        return TYPE_COLUMN;
    }

    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case TYPE_COLUMN:
            case VALUE_COLUMN:
                return Object.class;
            case NOTE_COLUMN:
                return String.class;
            default:
                throw new IllegalArgumentException("Column " + column + " is out of bounds");
        }
    }

    public Object getValueAt(Object node, int column) {
        Annotation anno = (Annotation) node;
        switch (column) {
            case TYPE_COLUMN:
                if (anno instanceof GroupAnnotation) {
                    // TODO: It would be nice if groups could have the name of what they represent.
                    return "group";
                } else {
                    return ((Annotation) node).getInterpreter();
                }
            case VALUE_COLUMN:
                if (anno instanceof GroupAnnotation) {
                    // XXX: Later we do want to support groups with interpretations.
                    return null;
                } else {
                    return anno.interpret(binary);
                }
            case NOTE_COLUMN:
                return anno.getNote();
            default:
                throw new IllegalArgumentException("Column " + column + " is out of bounds");
        }
    }

    public boolean isCellEditable(Object node, int column) {
        return column == NOTE_COLUMN;
    }

    public void setValueAt(Object value, Object node, int column) {
        if (column == NOTE_COLUMN) {
            if (node instanceof MutableAnnotation) {
                ((MutableAnnotation) node).setNote((String) value);
                // TODO: Set a dirty flag somewhere.
            } else {
                // TODO
            }
        } else {
            throw new UnsupportedOperationException("Editing not supported on column " + column);
        }
    }

    public void annotationsChanged(AnnotationCollectionEvent event) {
        // TODO: This will change a bit once we have some more structure.
        fireTreeStructureChanged(new TreeModelEvent(this, new Object[] { getRoot() }));
    }
}

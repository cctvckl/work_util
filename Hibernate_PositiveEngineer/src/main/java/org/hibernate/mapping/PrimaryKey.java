/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.mapping;
import org.hibernate.dialect.Dialect;

import java.util.Iterator;

/**
 * A primary key constraint
 * @author Gavin King
 */
public class PrimaryKey extends Constraint {

    public String sqlConstraintString(Dialect dialect) {
        StringBuilder buf = new StringBuilder("primary key (");
        Iterator iter = getColumnIterator();
        while ( iter.hasNext() ) {
            String quotedName = ((Column) iter.next()).getQuotedName(dialect);
            quotedName = processStr(quotedName);
            buf.append(quotedName);
            if ( iter.hasNext() ) buf.append(", ");
        }
        return buf.append(')').toString();
    }

    public String sqlConstraintString(Dialect dialect, String constraintName, String defaultCatalog, String defaultSchema) {
        StringBuilder buf = new StringBuilder(
                dialect.getAddPrimaryKeyConstraintString(constraintName)
        ).append('(');
        Iterator iter = getColumnIterator();
        while ( iter.hasNext() ) {
            buf.append( ( (Column) iter.next() ).getQuotedName(dialect) );
            if ( iter.hasNext() ) buf.append(", ");
        }
        return buf.append(')').toString();
    }

    public String generatedConstraintNamePrefix() {
        return "PK_";
    }



    private static String processStr(String para) {
        System.out.println("process primary key" + para);
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }
}

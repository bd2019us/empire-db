/*
 * ESTEAM Software GmbH
 */
package org.apache.empire.db.expr.compare;

import java.util.Set;

import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBCommandExpr;
import org.apache.empire.db.DBDatabase;


/**
 * This class is used for building up the SQL-Command for the EXISTS syntax.
 * <P>
 * 
 * @author ESTEAM software <A TARGET="esteam" HREF="http://www.esteam.de">www.esteam.de </A>
 */
public class DBExistsExpr extends DBCompareExpr
{
    public final DBCommandExpr cmd;
    public final DBCompareExpr compareExpr;

    /**
     * Constructs a DBExistsExpr object set the specified parameters to this object.
     * 
     * @param cmd the DBCommandExpr object
     */
    public DBExistsExpr(DBCommandExpr cmd)
    {
        this(cmd, null);
    }

    /**
     * Constructs a DBExistsExpr object set the specified parameters to this object.
     * 
     * @param cmd the DBCommandExpr object
     * @param compareExpr The expression to append to the end of the exists statement
     */
    public DBExistsExpr(DBCommandExpr cmd, DBCompareExpr compareExpr)
    {
        this.cmd = cmd;
        this.compareExpr = compareExpr;
    }

    /**
     * Returns the current DBDatabase object.
     * 
     * @return the current DBDatabase object
     */
    @Override
    public DBDatabase getDatabase()
    {
        return cmd.getDatabase();
    }

    /**
     * @see org.apache.empire.db.DBExpr#addReferencedColumns(Set)
     */
    @Override
    public void addReferencedColumns(Set<DBColumn> list)
    {
        // nothing to do
    }

    /**
     * Creates the SQL-Command.
     * 
     * @param buf the SQL-Command
     * @param context the current SQL-Command context (CTX_DEFAULT, CTX_SELECT, CTX_NAME, CTX_VALUE)
     */
    @Override
    public void addSQL(StringBuilder buf, long context)
    {
        // Name Only ?
        if ((context & CTX_VALUE) == 0)
        {
            log.warn("cannot add name only of exits expression");
            return;
        }
        // Value Only ?
        if ((context & CTX_NAME) == 0)
        {
            log.warn("cannot add value only of exits expression");
            return;
        }
        buf.append(" exists (");
        cmd.getSelect(buf);

        if (compareExpr != null)
        {
            if (cmd instanceof DBCommand && ((DBCommand) cmd).getWhereConstraints() == null)
            {
                buf.append(" where ");
            } 
            else
            {
                buf.append(" and ");
            }
            buf.append("(");
            compareExpr.addSQL(buf, context);
            buf.append(") ");
        }
        buf.append(") ");
    }
    
    /**
     * Returns wheter the constraint should replace another one or not.
     * @return true it the constraints are mutually exclusive or false otherwise
     */
    @Override
    public boolean isMutuallyExclusive(DBCompareExpr other)
    {
    	if (other instanceof DBExistsExpr)
    	{
    		DBExistsExpr o = (DBExistsExpr)other;
    		if (cmd==o.cmd && compareExpr==o.compareExpr)
    			return true;
    	}
    	return false;
    }
    
}
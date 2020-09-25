package datawave.query.jexl.visitors;

import datawave.query.jexl.JexlASTHelper;
import datawave.query.jexl.LiteralRange;
import datawave.query.jexl.nodes.BoundedRange;
import datawave.webservice.common.logging.ThreadConfigurableLogger;

import org.apache.commons.jexl2.parser.ASTAndNode;
import org.apache.commons.jexl2.parser.ASTERNode;
import org.apache.commons.jexl2.parser.ASTGENode;
import org.apache.commons.jexl2.parser.ASTGTNode;
import org.apache.commons.jexl2.parser.ASTLENode;
import org.apache.commons.jexl2.parser.ASTLTNode;
import org.apache.commons.jexl2.parser.ASTNRNode;
import org.apache.commons.jexl2.parser.ASTReference;
import org.apache.commons.jexl2.parser.ASTReferenceExpression;
import org.apache.commons.jexl2.parser.JexlNode;
import org.apache.log4j.Logger;

/**
 * Visits an JexlNode tree, and tag bounded ranges using the SingleValueContext flag
 *
 * 
 *
 */
public class RangeTaggingVisitor extends RebuildingVisitor {
    private static final Logger log = ThreadConfigurableLogger.getLogger(RangeTaggingVisitor.class);
    
    /**
     * Tag ranges to ensure they are evaluated appropriately.
     *
     * @param script
     * @return The tree with ranges tagged
     */
    @SuppressWarnings("unchecked")
    public static <T extends JexlNode> T tagRanges(T script) {
        RangeTaggingVisitor visitor = new RangeTaggingVisitor();
        
        return (T) script.jjtAccept(visitor, null);
    }
    
    @Override
    public Object visit(ASTERNode node, Object data) {
        return node;
    }
    
    @Override
    public Object visit(ASTNRNode node, Object data) {
        return node;
    }
    
    @Override
    public Object visit(ASTLTNode node, Object data) {
        return node;
    }
    
    @Override
    public Object visit(ASTGTNode node, Object data) {
        return node;
    }
    
    @Override
    public Object visit(ASTLENode node, Object data) {
        return node;
    }
    
    @Override
    public Object visit(ASTGENode node, Object data) {
        return node;
    }
    
    @Override
    public Object visit(ASTAndNode node, Object data) {
        Object ret = processRange(node, data);
        return (ret == null ? super.visit(node, data) : ret);
    }
    
    @Override
    public Object visit(ASTReference node, Object data) {
        Object ret = processRange(node, data);
        return (ret == null ? super.visit(node, data) : ret);
    }
    
    @Override
    public Object visit(ASTReferenceExpression node, Object data) {
        Object ret = processRange(node, data);
        return (ret == null ? super.visit(node, data) : ret);
    }
    
    private Object processRange(JexlNode node, Object data) {
        LiteralRange range = JexlASTHelper.findRange().notDelayed().getRange(node);
        
        if (range != null) {
            if (BoundedRange.instanceOf(node)) {
                return node;
            } else {
                return BoundedRange.create(node);
            }
        } else {
            return null;
        }
    }
}

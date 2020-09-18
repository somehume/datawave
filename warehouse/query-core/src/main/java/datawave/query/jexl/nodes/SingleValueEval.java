package datawave.query.jexl.nodes;

import org.apache.commons.jexl2.parser.JexlNode;
import org.apache.commons.jexl2.parser.JexlNodes;

/**
 * This is a node that can wrap an expression (normally a range) to mark that the source expression can only be evaluated against one value within a multivalued
 * field.
 */
public class SingleValueEval extends QueryPropertyMarker {
    
    public SingleValueEval(int id) {
        super(id);
    }
    
    public SingleValueEval() {
        super();
    }
    
    /**
     * This will create a structure as follows around the specified node: Reference (this node) Reference Expression AND Reference Reference Expression
     * Assignment Reference Identifier:SingleValueEval True node (the one specified
     *
     * Hence the resulting expression will be ((SingleValueEval = True) AND {specified node})
     *
     * @param node
     */
    public SingleValueEval(JexlNode node) {
        super(node);
    }
    
    /**
     * @param node
     * @return
     */
    public static SingleValueEval create(JexlNode node) {
        
        JexlNode parent = node.jjtGetParent();
        
        SingleValueEval expr = new SingleValueEval(node);
        
        if (parent != null) {
            JexlNodes.replaceChild(parent, node, expr);
        }
        
        return expr;
    }
    
    /**
     * A routine to determine whether an and node is actually an singe value evaluation marker. The reason for this routine is that if the query is serialized
     * and deserialized, then only the underlying assignment will persist.
     * 
     * @param node
     * @return true if this and node is a single value evaluation marker
     */
    public static boolean instanceOf(JexlNode node) {
        return instanceOf(node, SingleValueEval.class);
    }
    
    /**
     * A routine to determine get the node which is the source of the single value evaluation (i.e. the underlying range)
     * 
     * @param node
     * @return the source node or null if not an an singe value evaluation marker
     */
    public static JexlNode getSingleValueSource(JexlNode node) {
        return getQueryPropertySource(node, SingleValueEval.class);
    }
    
}

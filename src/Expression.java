/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 2
 * Section: Z
 * Student Name: Zhili Mai
 * Student eecs account:  mai1015
 * Student ID number:  215234842
 **********************************************************/
import java.util.Scanner;
import java.util.Stack;

public class Expression {
    enum Type {
        Operand,
        Operator,
    }

    /**
     * Test Normal Expression
     */
    private static final String TEST[] = {
            "a+b*c)/d))+c)",
            "a+b+c+d)))",
            "1+2+3))*4*5))",
            "a\n+\tst)*a+_c))",
            "a+aa+a))",
            "a + 20)/b – c)*53.4 – d)))"
    };

    /**
     * Illegal Test Expression
     */
    private static final String ERROR[] = {
            "a+b+c)",
            "s+)",
            "a a a",
            "+",
            "s+\n+ b"
    };

    /**
     * Set of Operator
     */
    private static final String OPERATORS = "+–*/";
    /**
     * Set of empty space
     */
    private static final String SPACE = " \t\n";

    /**
     * Original expression
     */
    private String exp;
    /**
     * Pointer to current location
     */
    private int eP;
    /**
     * Expression Stack
     */
    private Stack<String> exps;
    /**
     * Operator Stack
     */
    private Stack<String> ops;
    /**
     * Result UPPE
     */
    private String UPPE;
    /**
     * Result FPIE
     */
    private String FPIE;

    /**
     * RPIE Expression converter
     * Initialize value and try to convert the expression.
     * @param exp RPIE expression
     */
    public Expression(String exp) {
        if (exp.isEmpty()) throw new IllegalArgumentException("Empty expression");
        this.exp = exp;
        eP = 0;
        exps = new Stack<>();
        ops = new Stack<>();
        UPPE = "";
        convert();
        "".replace(" ", "");
    }

    /**
     * Return the value of the UPPE expression
     * @return UPPE expression in String
     */
    public String getUPPE() {
        return UPPE;
    }

    /**
     * Return the value of the FPIE expression
     * @return UPPE expression in String
     */
    public String getFPIE() {
        return FPIE;
    }

    /**
     * if current pointer points to a bracket
     * @return true if next char is right bracket
     */
    private boolean hasNextBracket() {
        return hasNext() && exp.charAt(eP) == ')';
    }

    /**
     * if the string is finished
     * @return true if string at the end
     */
    private boolean hasNext() {
        return eP < exp.length();
    }

    /**
     * read specify type needed
     * @param type type of string needed to read
     * @return the String needed to read
     * @throws IllegalArgumentException cannot read specified string
     */
    private String read(Type type) {
        StringBuilder sb = new StringBuilder();
        for (; eP < exp.length(); eP++) {
            char c = exp.charAt(eP);
            if (type == Type.Operator) {
                if (SPACE.indexOf(c) != -1) continue;
                if (c == ')') break;
                if (OPERATORS.indexOf(c) != -1) {
                    sb.append(c);
                    eP++;
                    break;
                } else {
                    throw new IllegalArgumentException("Invalid RPIE: Mismatch Operator");
                }
            } else if (type == Type.Operand) {
                if (OPERATORS.indexOf(c) != -1 || c == ')') {
                    if (sb.length() > 0) break;
                    throw new IllegalArgumentException("Invalid RPIE: Mismatch Operand");
                }
                if (SPACE.indexOf(c) != -1) {
                    if (sb.length() > 0) {
                        eP++;
                        break;
                    }
                    continue;
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * combine two into a FPIE exp
     * @param a first operand
     * @param b second operand
     * @param o operator
     */
    private void combineExp(String a, String b, String o) {
        exps.push(String.format("(%s %s %s)", a, o, b));
    }

    /**
     * combine two into UPPE
     * @param a first operand
     * @param b second operand
     * @param o operator
     */
    private void combineUppe(String a, String b, String o) {
        if (!a.startsWith("(")) UPPE += a + " ";
        if (!b.startsWith("(")) UPPE += b + " ";
        UPPE += o + " ";
    }

    /**
     * Combine two operands with an operators
     * @param a first operand
     * @param b second operand
     * @param o operator
     */
    private void combine(String a, String b, String o) {
        combineExp(a, b, o);
        combineUppe(a, b, o);
    }

    /**
     * Take two operands with and an operators from stack
     */
    private void combine() {
        String o = ops.pop();
        String b = exps.pop();
        String a = exps.pop();
        combine(a, b, o);
    }

    /**
     * if lower priority operation, then combine last one first
     */
    private void priorityCombine() {
        String o = ops.pop();
        String b = exps.pop();
        if (ops.size() > 0 && (o.equals("+") || o.equals("–"))) {
            priorityCombine();
        }
        String a = exps.pop();
        combine(a, b, o);
    }

    /**
     * try to convert specified RPIE String into FPIE and UPPE
     */
    private void convert() {
        boolean op = false;
        while (hasNext()) {
            if (op) { // determine what to read next
                // start looking for operator
                String o = read(Type.Operator);
                if (o.isEmpty()) {// if find empty string, might found a bracket
                    if (hasNextBracket()) {
                        eP++;
                        combine(); // combine the bracket
                    }
                } else {
                    op = false;
                    ops.push(o);
                }
            } else {
                // start looking for operand
                String b = read(Type.Operand);
                op = true;
                if (hasNextBracket()) {// highest priority
                    String a = exps.pop();
                    String o = ops.pop();
                    combine(a, b, o);
                    eP++;
                } else {
                    // priority combine the exp with out right bracket
//                    if (ops.size() > 2) { // too many operator, it is possible to combine
//                        String o = ops.pop();
//                        combine();
//                        ops.push(o);
//                    } else if (ops.size() > 1) { // if this is a low priority, then try to combine last one
//                        String o = ops.pop();
//                        if (o.equals("+") || o.equals("–")) {
//                            combine();
//                        }
//                        ops.push(o);
//                    }
                    exps.push(b);
                }
            }
        }
        if (!ops.isEmpty() || exps.size() > 1) { // not tolerate any lose of right bracket
            throw new IllegalArgumentException("Invalid RPIE: Illegal RPIE");
        }
//        while (!ops.isEmpty()) {// tolerate not finished right bracket
//            try {
//                priorityCombine();
//            } catch (EmptyStackException e) {
//                throw new IllegalArgumentException("Invalid RPIE: Illegal ending with Operator");
//            }
//        }
        FPIE = exps.pop();
    }

    public static void main(String[] args) {
//        System.out.println("--------------------------");
//        test();
//        System.out.println("--------------------------");
//        testError();
        System.out.print("Input your RPIE exp: ");
        String str = new Scanner(System.in).nextLine();
        Expression e = new Expression(str);
        System.out.println(e.exps);
        System.out.println(e.ops);
        System.out.print("Result UPPE exp: ");
        System.out.println(e.getUPPE());
        System.out.print("Result FPIE exp: ");
        System.out.println(e.getFPIE());
    }

    public static void test() {
        for (int i = 0; i < TEST.length; i++) {
            System.out.printf("Test exp %d: %s\n", i + 1, TEST[i]);
            Expression e = new Expression(TEST[i]);
            System.out.printf("Result UPPE exp: %s\n", e.getUPPE());
            System.out.printf("Result FPIE exp: %s\n", e.getFPIE());
            System.out.println();
        }
    }

    public static void testError() {
        for (int i = 0; i < ERROR.length; i++) {
            try {
                System.out.printf("Test exp %d: %s\n", i + 1, ERROR[i]);
                Expression e = new Expression(ERROR[i]);
                System.out.printf("Result UPPE exp: %s\n", e.getUPPE());
                System.out.printf("Result FPIE exp: %s\n", e.getFPIE());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
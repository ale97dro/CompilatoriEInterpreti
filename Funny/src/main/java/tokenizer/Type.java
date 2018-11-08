package tokenizer;

/**
 * tokenizer.Token type
 */
public enum Type {
    START_KEY_WORD,
    NIL, FALSE, TRUE,
    IF, IFNOT, THEN, ELSE, FI,
    WHILE, WHILENOT, DO, OD,
    PRINT, PRINTLN,
    END_KEY_WORD,
    START_SIMPLE_TOKEN,
    SEMICOLON, COMMA, BRACKETOPEN, BRACKETCLOSE, BRACEOPEN, BRACECLOSE,
    END_SIMPLE_TOKEN,
    ID, NUM, STRING,
    ARROW,
    NOT,
    STAR, SLASH, PERCENTAGE,
    PLUS, MINUS,
    LESS, LESSEQUAL, GREATER, GREATEREQUAL,
    EQUAL, NOTEQUAL,
    AND,
    OR,
    ASSIGN, ADDASSIGN, MINUSASSIGN, MULTASSIGN, DIVASSIGN, PERCENTASSIGN,
    EOS,
    UNKNOW,
}


/*


 case ';':
                temp = new Token(Type.SEMICOLON);
                break;
            case ',':
                temp = new Token(Type.COMMA);
                break;
            case '(':
                temp = new Token(Type.);
                break;
            case ')':
                temp = new Token(Type.);
                break;
            case '{':
                temp = new Token(Type.);
                break;
            case '}':
                temp = new Token(Type.);
                break;
 */




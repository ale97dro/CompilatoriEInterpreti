public enum Type
{
   EOS((char)-1), VERT('|'), HORIZ('-'); //EOS carattere di fine file (-1) restituito da Reader

   private final char value;

   Type(char value)
   {
       this.value = value;
   }

   public char getValue()
   {
       return value;
   }
}

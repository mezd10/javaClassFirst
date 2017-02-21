public  final class Number {

    private final int numberBefore;

    private final int numberAfter;

    private final int[] before;

    private final int[] after;

    public Number(int[] before,
                  int[] after,
                  int numberBefore, int numberAfter){
        this.numberBefore = numberBefore;
        this.numberAfter = numberAfter;
        this.before = before;
        this.after = after;
    }

    @Override
    public String toString(){

        int i = 0;
        String strNumberBefore = "";
        while (numberBefore != i){
            strNumberBefore = strNumberBefore + String.valueOf(before[i]);
            i ++;
        }

        String strNumberAfter = "";
        while (numberAfter != i){
            strNumberAfter = strNumberAfter + String.valueOf(after[i]);
            i ++;
        }

        String number = strNumberBefore + "." + strNumberAfter;
        return number;
    }

}

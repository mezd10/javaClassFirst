import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FractionalNumber {

    private final int before;

    private final int after;

    private final List<Integer> numberUnion;

    private boolean sign = true;


    public FractionalNumber(boolean sign, int before, int after,
                            List<Integer> numberUnion) {

        this.sign = sign;
        this.before = before;
        this.after = after;
        this.numberUnion = numberUnion;
    }

    public FractionalNumber(String number) throws NumberFormatException {
        List<Integer> result = new ArrayList<>();
        int before = 0;
        int after = 0;
        int i = 0;
        Pattern p = Pattern.compile("((^-)|(^))((\\d+$)|(\\d+\\.\\d+$))");
        Matcher m = p.matcher(number);
        if (!m.find()) throw new NumberFormatException();
        if (m.group().charAt(0) == '-') {
            sign = false;
            i = 2;
            result.add(m.group().codePointAt(1) - '0');
            before++;
        }

        for (; i < m.group().length(); i++) {
            if (m.group().charAt(i) == '.') continue;
            if (after != 0 || (i != 0 && m.group().charAt(i - 1) == '.')) {
                after++;
                result.add(m.group().codePointAt(i) - '0');
            } else {
                before++;
                result.add(m.group().codePointAt(i) - '0');
            }
        }
        this.before = before;
        this.after = after;
        this.numberUnion = result;
    }

    public FractionalNumber plus(FractionalNumber other) {

        List<Integer> result = new ArrayList<>();
        int maxAfter = Math.max(after, other.after);

        if (sign == other.sign) {

            for (int i = 0; i < maxAfter - after; i++) {
                numberUnion.add(0);
            }

            for (int i = 0; i < maxAfter - other.after; i++) {
                other.numberUnion.add(0);
            }

            int maxBefore = Math.max(before, other.before);

            for (int i = 0; i < maxBefore - before; i++) {
                numberUnion.add(0, 0);
            }

            for (int i = 0; i < maxBefore - other.before; i++) {
                other.numberUnion.add(0, 0);
            }

            int excess = 0;

            for (int i = maxBefore + maxAfter - 1; i >= 0; i--) {
                result.add(0, (numberUnion.get(i) + other.numberUnion.get(i) + excess) % 10);

                if (numberUnion.get(i) + other.numberUnion.get(i) + excess > 9) {
                    excess = 1;
                } else {
                    excess = 0;
                }
            }

            if (excess > 0) {
                result.add(0, 1);
            }

            List<Integer> maxNumber = maxUnionNumber(this, other);

            if (maxNumber.equals(other.numberUnion)) {
                sign = other.sign;
            }

            return new FractionalNumber(sign, result.size() - maxAfter, maxAfter, result);
        } else {
            other.sign = sign;
            FractionalNumber number = this;
            FractionalNumber number1 = other;

            return number.minus(number1);
        }
    }


    private List<Integer> maxUnionNumber(FractionalNumber numb1,
                                         FractionalNumber numb2) {
        List<Integer> list;
        List<Integer> otherList;

        if (numb1.equals(numb2)) return numberUnion;
        if (numb1.before > numb2.before) return numb1.numberUnion;
        if (numb1.before < numb2.before) return numb2.numberUnion;
        if (numb1.before == numb2.before && Math.abs(numb1.numberUnion.get(0)) == Math.abs(numb2.numberUnion.get(0))) {
            if (numb1.numberUnion.get(0) >= 0) return numb1.numberUnion;
            else return numb2.numberUnion;
        } else {
            list = numb1.numberUnion;
            otherList = numb2.numberUnion;
            int max = Math.max(list.size(), otherList.size());
            for (int i = 0; i < max - list.size(); i++) {
                list.add(0);
            }
            for (int i = 0; i < max - otherList.size(); i++) {
                otherList.add(0);
            }
            int i = 0;
            int sign = 0;
            int sign1 = 0;
            if (list.get(0) < 0) {
                sign = 1;
                int per = list.get(0);
                per *= -1;
                list.remove(0);
                list.add(0, per);
            }
            if (otherList.get(0) < 0) {
                sign1 = 1;
                int per = otherList.get(0);
                per *= -1;
                otherList.remove(0);
                otherList.add(0, per);
            }
            while (true) {
                if (list.get(i) > otherList.get(i)) {
                    if (sign == 1) {
                        int per = list.get(0);
                        per *= -1;
                        list.remove(0);
                        list.add(0, per);
                    }
                    return list;
                }
                if (list.get(i) < otherList.get(i)) {
                    if (sign1 == 1) {
                        int per = otherList.get(0);
                        per *= -1;
                        otherList.remove(0);
                        otherList.add(0, per);
                    }
                    return otherList;
                }
                i++;
            }
        }
    }

    public FractionalNumber minus(FractionalNumber other) {

        if ((sign == true && other.sign == true) || (sign == false && other.sign == false)) {

            List<Integer> result = new ArrayList<>();
            int maxAfter = Math.max(after, other.after);

            for (int i = 0; i < maxAfter - after; i++) {
                numberUnion.add(0);
            }

            for (int i = 0; i < maxAfter - other.after; i++) {
                other.numberUnion.add(0);
            }

            int maxBefore = Math.max(before, other.before);

            for (int i = 0; i < maxBefore - before; i++) {
                numberUnion.add(0, 0);
            }

            for (int i = 0; i < maxBefore - other.before; i++) {
                other.numberUnion.add(0, 0);
            }

            List<Integer> maxNumber = maxUnionNumber(this, other);
            List<Integer> minNumber;

            if (maxNumber.equals(this.numberUnion)) {
                minNumber = other.numberUnion;
            } else {
                minNumber = numberUnion;
                sign = false;
            }

            Collections.reverse(maxNumber);
            Collections.reverse(minNumber);

            int shortcoming = 0;

            for (int i = 0; i < maxNumber.size(); i++) {
                if (maxNumber.get(i) - minNumber.get(i) - shortcoming < 0) {
                    result.add(0, maxNumber.get(i) - minNumber.get(i) - shortcoming + 10);
                    shortcoming = 1;
                } else {
                    result.add(0, maxNumber.get(i) - minNumber.get(i) - shortcoming);
                    shortcoming = 0;
                }
            }

            return new FractionalNumber(sign, result.size() - maxAfter, maxAfter, result);
        } else {
            other.sign = sign;
            FractionalNumber number = this;
            FractionalNumber number1 = other;

            return number.plus(number1);
        }
    }

    public FractionalNumber multiplication(FractionalNumber other) {

        if ((sign == true && other.sign == true) || (sign == false && other.sign == false)) sign = true;
        else sign = false;

        List<Integer> result = new ArrayList<>();
        Collections.reverse(numberUnion);
        Collections.reverse(other.numberUnion);
        int excess;
        int rez;
        for (int i = 0; i < numberUnion.size(); i++) {
            excess = 0;
            for (int j = 0; j < other.numberUnion.size(); j++) {
                if (i + j > result.size() - 1) {
                    rez = numberUnion.get(i) * other.numberUnion.get(j) + excess;
                    result.add(rez % 10);
                } else {
                    rez = numberUnion.get(i) * other.numberUnion.get(j) + excess + result.get(i + j);
                    result.set(i + j, rez % 10);
                }
                excess = rez / 10;
            }
            if (i + other.numberUnion.size() > result.size() - 1) {
                result.add(excess);
            } else {
                result.set(i + other.numberUnion.size(), excess);
            }
        }
        int resultAfter = after + other.after;
        int resultBefor = result.size() - resultAfter;
        Collections.reverse(result);

        return new FractionalNumber(sign, resultBefor, resultAfter, result);
    }

    public FractionalNumber rounding(int order) {
        List<Integer> result = new ArrayList<>();
        List<Integer> thisResult = new ArrayList<>();
        if (after <= order) {
            return new FractionalNumber(sign, numberUnion.size() - after, after, numberUnion);
        } else {
            thisResult.addAll(numberUnion.subList(0, before + order));
            int resultBefore;
            int resultAfter;
            if (numberUnion.get(before + order) >= 5) {
                resultBefore = 1;
                resultAfter = order;
                result.add(1);
                for (int i = order; i > 0; i--) {
                    result.add(0, 0);
                }
                return new FractionalNumber(sign, before, order, thisResult).plus(new FractionalNumber(sign, resultBefore, resultAfter, result));
            } else {
                resultBefore = before;
                resultAfter = order;
                result.addAll(numberUnion.subList(0, before + order));
                return new FractionalNumber(sign, resultBefore, resultAfter, result);
            }
        }
    }

    public FractionalNumber(double n) {
        this(Double.toString(n));
    }

    public double toDouble() {
        double result = 0.0;
        for (int i = 1; i <= before; i++) {
            result = result + numberUnion.get(i - 1) * Math.pow(10, before - i);
        }
        for (int i = 1; i <= after; i++) {
            result = result + (double) numberUnion.get(i + before - 1) / (double) Math.pow(10, i);
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (sign == false) result.append("-");
        for (int i = 0; i < before; i++) {
            result.append(numberUnion.get(i));
        }
        if (after != 0) result.append(".");
        for (int j = before; j < before + after; j++) {
            result.append(numberUnion.get(j));
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FractionalNumber number = (FractionalNumber) o;

        if (before != number.before) return false;
        if (after != number.after) return false;
        return numberUnion.equals(number.numberUnion);

    }

    @Override
    public int hashCode() {
        int result = before;
        result = 31 * result + after;
        result = 31 * result + numberUnion.hashCode();
        return result;
    }
}


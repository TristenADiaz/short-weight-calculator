package math;

public class WeightMath {

    public WeightMath() {

    }

    public static final int CARDBOARD_TOTE = 60, PLASTIC_TOTE = (100+CARDBOARD_TOTE);
    public enum Product{
        BSB("BSB",6.69,0.9331),
        TENDER("TENDER",5.98,0.9402),
        WING("WING",8.79,0.9121),
        BS_THIGH("BS THIGH",7.69,0.9231),
        LEG_MEAT("LEG MEAT",10.13,0.8987);

        String product;
        double percentage;
        double tare;


        Product(String product, double percentage, double tare) {
            this.product = product;
            this.percentage = percentage;
            this.tare = tare;
        }

        public String getProduct() {
            return product;
        }
        public double getPercentage() {
            return percentage;
        }
        public double getTare() {
            return tare;
        }
    }

    public static int findToteWeight(int toteWeight, boolean isCardboard, Product tote) {

        int res;
        try {
            int weight = isCardboard ? (toteWeight -CARDBOARD_TOTE) : (toteWeight - PLASTIC_TOTE);
            int shortWeight = Math.toIntExact(Math.round(weight * tote.getTare()));
            System.out.println(shortWeight);
            System.out.println(weight);
            System.out.println(tote.getTare());
            res = shortWeight;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static int findOrderWeight(int toteWeight, boolean isCardboard, Product tote) {
        int res;
        try {
            int weight = isCardboard ? (toteWeight -CARDBOARD_TOTE) : (toteWeight - PLASTIC_TOTE);
            int shortWeight = Math.toIntExact(Math.round(weight / tote.getTare()));
            res = Math.subtractExact(toteWeight, shortWeight);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}

package YandexACA.StokProfit;

public class StockProfitCalculator {

    public static int findMaxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0; // If there are no prices or only one price, no profit can be made.
        }

        int maxProfit = 0;
        int minPrice = prices[0]; // Initialize the minimum price to the first day's price.

        for (int i = 1; i < prices.length; i++) {
            int currentProfit = prices[i] - minPrice;

            // Update the maximum profit if a higher profit is found.
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
            }

            // Update the minimum price if a lower price is found.
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        // Example usage:
        int[] prices = {6, 3, 1, 2, 5, 4};
        int maxProfit = findMaxProfit(prices);
        System.out.println("Maximum profit: " + maxProfit);
    }
}


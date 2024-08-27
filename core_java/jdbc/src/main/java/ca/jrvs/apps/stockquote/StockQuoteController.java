package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.services.*;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class StockQuoteController {

    private QuoteService quoteService;
    private PositionService positionService;

    public StockQuoteController(QuoteService quoteService, PositionService positionService) {
        this.quoteService = quoteService;
        this.positionService = positionService;
    }

    /**
     * User interface for our application
     */
    public void initClient() {
        Scanner input = new Scanner(System.in);
        while (true) {
            showMainMenu();

            if (!input.hasNextInt()) {
                System.out.print("Invalid option. Please try again.");
                input.next();
                continue;
            }

            int option = input.nextInt();

            switch (option) {

                case 1:
                    viewStockQuote(input);
                    break;
                case 2:
                    viewCurrentPositions(input);
                    break;
                case 3:
                    buyStock(input);
                    break;
                case 4:
                    sellStock(input);
                    break;
                case 5:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewStockQuote(Scanner input) {
        System.out.print("Enter stock ticker to view: ");
        String ticker = input.next();

        try {
            Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker.toUpperCase());
            System.out.println(quote.get());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ticker. Please try again.");
        }
    }

    private void viewCurrentPositions(Scanner input) {
        Iterable<Position> posList = positionService.showAll();
        for (Position pos : posList) {
            System.out.println(pos);
        }
    }

    private void buyStock(Scanner input) {
        System.out.print("Enter stock ticker to buy: ");
        String ticker = input.next().toUpperCase();
        Optional<Quote> quote;

        try {
            quote = quoteService.fetchQuoteDataFromAPI(ticker);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ticker. Please try again.");
            return;
        }

        System.out.printf("Buying: " + quote.get().getTicker());
        System.out.print("\nEnter number of shares to buy: ");
        int numberOfShares;
        try {
            numberOfShares = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid number of shares, check available shares. Please try again.");
            return;
        }

        try {
            Position position = positionService.buy(ticker, numberOfShares, quote.get().getPrice());
            if (position == null) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid number of shares, check available shares. Please try again.");
            return;
        }

        System.out.printf("Successfully bought %d shares of %s at %f.", numberOfShares, ticker, quote.get().getPrice());
    }

    private void sellStock(Scanner input) {
        System.out.print("Enter stock ticker to sell: ");
        String ticker = input.next().toUpperCase();

        try {
            positionService.sell(ticker);
        } catch (IllegalArgumentException e) {
            System.out.printf("No shares owned for %s.", ticker);
            return;
        }

        System.out.printf("Successfully sold all %s.", ticker);

    }

    private void showMainMenu() {
        System.out.println("\nStock Quote Application");
        System.out.println("1. View a Stock Quote");
        System.out.println("2. View your Current Positions");
        System.out.println("3. Buy a Stock");
        System.out.println("4. Sell a Stock");
        System.out.println("5. Exit");
        System.out.print("Select a menu option: ");
    }

}

package lotto.view;

import lotto.domain.LottoResult;
import lotto.domain.Lotto;
import lotto.domain.enums.Rank;

import java.util.List;

public class OutputView {
    public static void printLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
    }

    public static void printResults(LottoResult result, int purchaseAmount) {
        System.out.println("\n당첨 통계");
        System.out.println("---");
        printRankStatistics(result);
        printProfitRate(result, purchaseAmount);
    }

    private static void printRankStatistics(LottoResult result) {
        for (int i = Rank.values().length - 1; i >= 0; i--) {
            Rank rank = Rank.values()[i];
            int count = result.getCountByRank(rank);
            System.out.println(formatRankMessage(rank, count));
        }
    }

    private static String formatRankMessage(Rank rank, int count) {
        if (rank == Rank.SECOND) {
            return rank.getMatchCount() + "개 일치, 보너스 볼 일치 ("
                + formatPrize(rank.getPrize()) + ") - " + count + "개";
        }
        return rank.getMatchCount() + "개 일치 ("
            + formatPrize(rank.getPrize()) + ") - " + count + "개";
    }

    private static String formatPrize(long prize) {
        return String.format("%,d원", prize);
    }

    private static void printProfitRate(LottoResult result, int purchaseAmount) {
        double profitRate = result.calculateProfitRate(purchaseAmount);
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);
    }

    public static void printError(String message) {
        System.out.println(message);
    }
}

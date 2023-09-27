import java.util.ArrayList;
import java.util.Scanner;

class Player {
    String name;

    Player(String name) {
        this.name = name;
    }
}

class Team {
    String name;
    ArrayList<Player> players;

    Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    void addPlayer(Player player) {
        players.add(player);
    }
}

class CricketGame {
    Team team1;
    Team team2;
    int team1Score;
    int team2Score;

    void welcomeMsg() {
        System.out.println("Welcome to the cricket score board");
        System.out.println("In this game, a team is allowed to score the following:");
        System.out.println("1: for a single (causes change in strike)");
        System.out.println("2: for a double");
        System.out.println("3: for a triple (causes change of strike)");
        System.out.println("4: for a four (boundary)");
        System.out.println("W: for a wicket (next batsman comes in)");
        System.out.println("WD: for a wide (need to bowl the same delivery)");
    }

    void getPlayers(Team team) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= 4; i++) {
            System.out.print("Enter name of player " + i + " for " + team.name + ": ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);
            team.addPlayer(player);
        }
        scanner.close();
    }

    String setOver(int ballNumber) {
        int first = ballNumber / 7;
        int second = ballNumber % 7;
        return first + "." + second;
    }

    void overSummary(Team team, String over, int wicket, int score) {
        System.out.println();
        System.out.println("END of OVER");
        System.out.println(team.name + " " + score + "/" + wicket + " " + over);
    }

    void firstInnings(Team team) {
        int ballCounter = 1;
        int wicketCounter = 0;
        int teamScore = 0;
        int tempOver = 1;
        Scanner scanner = new Scanner(System.in);

        while (ballCounter <= 12 && wicketCounter < 3) {
            String over = setOver(ballCounter);
            if (Integer.parseInt(over.substring(0, 1)) == tempOver) {
                overSummary(team, over, wicketCounter, teamScore);
                tempOver++;
            }
            
            System.out.print("Enter the run for over " + over + "? ");
            String run = scanner.nextLine();

            if (run.equals("0") || run.equals("1") || run.equals("2") || run.equals("3") || run.equals("4") || run.equals("6")) {
                teamScore += Integer.parseInt(run);
                ballCounter++;
            } else if (run.equals("WD")) {
                teamScore++;
            } else if (run.equals("W")) {
                wicketCounter++;
                ballCounter++;
            } else {
                System.out.println("Enter a valid run [1, 2, 3, 4, 6, W, WD]");
            }
        }
        
        overSummary(team, setOver(ballCounter - 1), wicketCounter, teamScore);
        System.out.println();
        System.out.println("End of first innings");
        
        if (team == team1) {
            team1Score = teamScore;
        } else if (team == team2) {
            team2Score = teamScore;
        }
        scanner.close();
    }

    void secondInnings() {
        System.out.println("Second innings summary for " + team2.name);
        int ballCounter = 1;
        int wicketCounter = 0;
        int team2Score = 0;
        int tempOver = 1;
        Scanner scanner = new Scanner(System.in);

        while (ballCounter <= 12 && wicketCounter < 3) {
            String over = setOver(ballCounter);
            if (Integer.parseInt(over.substring(0, 1)) == tempOver) {
                overSummary(team2, over, wicketCounter, team2Score);
                tempOver++;
            }

            System.out.print("Enter the run for over " + over + "? ");
            String run = scanner.nextLine();

            if (run.equals("0") || run.equals("1") || run.equals("2") || run.equals("3") || run.equals("4") || run.equals("6")) {
                team2Score += Integer.parseInt(run);
                ballCounter++;
            } else if (run.equals("WD")) {
                team2Score++;
            } else if (run.equals("W")) {
                wicketCounter++;
                ballCounter++;
            } else {
                System.out.println("Enter a valid run [1, 2, 3, 4, 6, W, WD]");
            }
        }

        overSummary(team2, setOver(ballCounter - 1), wicketCounter, team2Score);
        System.out.println();
        System.out.println("End of " + team2.name + "'s innings");
        System.out.println(team1.name + " needs " + (team2Score + 1) + " runs to win.");
        scanner.close();
    }

    void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            welcomeMsg();
            team1 = new Team(scanner.nextLine());
            team2 = new Team(scanner.nextLine());
            getPlayers(team1);
            getPlayers(team2);
            firstInnings(team1);
            secondInnings();
            finalSummary(team1.name, team2.name, team1Score, team2Score);
            while (true) {
                System.out.print("Do you want to play again? (Y/N) ");
                String answer = scanner.nextLine();
                if (answer.equals("Y") || answer.equals("N")) {
                    if (answer.equals("N")) {
                        scanner.close(); // Close the scanner to prevent resource leak
                        return;
                    }
                    break;
                } else {
                    System.out.println("Enter a valid choice (Y/N)");
                }
            }
        scanner.close();
        }
    }   

    void finalSummary(String team1Name, String team2Name, int team1Score, int team2Score) {
        System.out.println("Final Summary:");
        System.out.println(team1Name + ": " + team1Score + " runs");
        System.out.println(team2Name + ": " + team2Score + " runs");

        if (team1Score > team2Score) {
            System.out.println(team1Name + " wins by " + (team1Score - team2Score) + " runs!");
        } else if (team1Score < team2Score) {
            System.out.println(team2Name + " wins by " + (team2Score - team1Score) + " runs!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        CricketGame game = new CricketGame();
        game.startGame();
    }
}
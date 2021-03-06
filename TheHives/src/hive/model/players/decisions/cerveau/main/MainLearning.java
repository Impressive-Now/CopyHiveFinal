/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.model.players.decisions.cerveau.main;

import hive.model.players.decisions.IADecisionLearning;
import hive.model.GameProgress;
import hive.model.game.Game;
import hive.model.game.PrecalculatedGame;
import hive.model.game.rules.GameStatus;
import hive.model.game.rules.HiveUtil;
import static hive.model.players.TeamColor.BLACK;
import static hive.model.players.TeamColor.WHITE;
import static hive.model.players.decisions.IA.Level.EHARD;
import hive.model.players.decisions.cerveau.AdamEtEve;
import hive.model.players.decisions.cerveau.EvaluationLearning;
import hive.model.players.decisions.cerveau.Mate;
import static hive.model.players.decisions.cerveau.RepertoryFamily.clearFile;
import hive.model.players.decisions.cerveau.Selection;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Coralie
 */
public class MainLearning {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int nbFirstChildren = 3;
        int nbGeneration = 1;
        String dossier[] = new String[2];
        dossier[0]="generationAlpha";
        dossier[1]="generationBeta";
        int dossierSuivant =0;
        int[] looseTurn = new int[nbFirstChildren];
        Selection select = new Selection(nbFirstChildren);
        AdamEtEve AE = new AdamEtEve(nbFirstChildren);
        clearFile("Family.txt");
        EvaluationLearning[] evaluations = AE.generate(dossier[dossierSuivant]);
        dossierSuivant = (dossierSuivant+1)%2;
        while (nbGeneration>0){
            for (int i = 0; i < nbFirstChildren; i++) {
                System.out.println("Nous sommes à la partie de fils" + i);
                Game game = PrecalculatedGame.get(PrecalculatedGame.Id.DEFAULT, new IADecisionLearning(evaluations[i]), new IADecisionLearning(EHARD));
                GameProgress progress = new GameProgress(game);
                GameStatus status;
                while ((status = game.rules.getStatus(game.state)) == GameStatus.CONTINUES && (HiveUtil.nbTurns(game.state) < 65)) {

                    if (HiveUtil.nbTurns(game.state) % 20 == 0 && game.state.turn.getCurrent().color ==WHITE) {
                        System.out.println("Turn : " + HiveUtil.nbTurns(game.state));
                    }
                    progress.doAction();

                }
                switch (status) {
                    case CURRENT_WINS:
                        System.out.println(game.state.turn.getCurrent().color + " gagne !");
                        System.out.println("Turn : " + HiveUtil.nbTurns(game.state));
                        if (game.state.turn.getCurrent().color == WHITE) {
                            select.addVictory(i);
                        } else {
                            looseTurn[i]=HiveUtil.nbTurns(game.state);
                        }
                        break;
                    case OPPONENT_WINS:
                        System.out.println(game.state.turn.getOpponent().color + " gagne !");
                        System.out.println("Turn : " + HiveUtil.nbTurns(game.state));
                        if (game.state.turn.getOpponent().color == BLACK) {
                            looseTurn[i]=HiveUtil.nbTurns(game.state);
                        } else {
                            select.addVictory(i);
                        }
                        break;
                    default:
                        //select.addVictory(i);
                        break;
                }

            }
            //select.theBestLoosers(looseTurn);
            int[] winner = select.lesGagnants();
            Mate newGeneration;
            System.out.println("Les fils de la génération suivante : ");
            newGeneration = new Mate(evaluations[winner[0]].getEvalValues(), evaluations[winner[1]].getEvalValues(),evaluations[winner[2]].getEvalValues(), 12);
            AE.registerGen(dossier[dossierSuivant], newGeneration.son);
            evaluations =  AE.initGeneration(dossier[dossierSuivant]);
            dossierSuivant = (dossierSuivant+1)%2;
            nbGeneration--;
        }
    }

}

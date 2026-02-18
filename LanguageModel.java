import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
    private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     * seed value. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
    public void train(String fileName) {

    String window = "";
    char c;
    In in = new In(fileName);

    while ((!in.isEmpty()) && (window.length() < windowLength)) {
        c = in.readChar();
        window += c;
    }

    while (!in.isEmpty()) {
        c = in.readChar();
        
        List probs = CharDataMap.get(window);
        if (probs == null) {
            probs = new List();
            CharDataMap.put(window, probs);
        }
        probs.update(c);

        window += c;
        window = window.substring(1, window.length());
    }

    for (List probs : CharDataMap.values())
        calculateProbabilities(probs);
}
    

    /** Computes and sets the probabilities (p and cp fields) of all the
     * characters in the given list. */
    void calculateProbabilities(List probs) {               
                   
    int totalCount = 0;
    for (int i = 0; i < probs.getSize(); i++) {
        totalCount += probs.get(i).count;
    }
    double cumulativeProb = 0.0;
    for (int i = 0; i < probs.getSize(); i++) {
        CharData cd = probs.get(i);
        cd.p = (double) cd.count / totalCount;
        cumulativeProb += cd.p;
        cd.cp = cumulativeProb; 
    }


    }
        
    /** Returns a random character from the given probabilities list. */
   char getRandomChar(List probs) {
    double r = randomGenerator.nextDouble();
    for (int i = 0; i < probs.getSize(); i++) {
        CharData data = probs.get(i);
        if (data.cp >= r) return data.chr;
    }
    return probs.get(probs.getSize() - 1).chr; 
}


    /** Generates a random text, based on the probabilities that were learned during training. */
   public String generate(String initialText, int textLength) {
    if (initialText.length() < windowLength) {
        return initialText;
    }
    String generatedText = initialText; 
    for (int i = 0; i < textLength; i++) {
        String window = generatedText.substring(generatedText.length() - windowLength);
        
        List probs = CharDataMap.get(window);
        if (probs == null) {
            return generatedText; 
        }
        
        char nextChar = getRandomChar(probs);
        generatedText += nextChar;
    }
    
    return generatedText;
}
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String key : CharDataMap.keySet()) {
            List keyProbs = CharDataMap.get(key);
            str.append(key + " : " + keyProbs + "\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        int windowLength = Integer.parseInt(args[0]);
        String initialText = args[1];
        int generatedTextLength = Integer.parseInt(args[2]);
        boolean isRandom = args[3].equals("random");
        String fileName = args[4];

        LanguageModel lm;
        if (isRandom) {
            lm = new LanguageModel(windowLength);
        } else {
            lm = new LanguageModel(windowLength, 20);
        }
        lm.train(fileName);

        System.out.println(lm.generate(initialText, generatedTextLength));
    }
}
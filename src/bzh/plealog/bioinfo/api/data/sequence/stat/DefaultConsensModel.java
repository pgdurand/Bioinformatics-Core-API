package bzh.plealog.bioinfo.api.data.sequence.stat;

import java.util.ArrayList;
import java.util.List;

import bzh.plealog.bioinfo.api.data.sequence.DAlphabet;
import bzh.plealog.bioinfo.api.data.sequence.DLocation;
import bzh.plealog.bioinfo.api.data.sequence.DRulerModel;
import bzh.plealog.bioinfo.api.data.sequence.DSequence;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceAlignment;
import bzh.plealog.bioinfo.api.data.sequence.DSequenceInfo;
import bzh.plealog.bioinfo.api.data.sequence.DSymbol;
import bzh.plealog.bioinfo.data.sequence.DRulerModelImplem;

/**
 * This is a default implementation of a consensus model.
 * 
 * @author Patrick G. Durand
 */
public class DefaultConsensModel implements ConsensusModel, DSequence {
  private PositionSpecificMatrix psm_;
  private DSequenceAlignment     msa_;
  private ConsensusCell[]        consensus_;
  private DRulerModel            drm_;
  private DSequenceInfo          dsi_;

  private DefaultConsensModel() {
    dsi_ = new DSequenceInfo();
    dsi_.setName("consensus");
  }

  /**
   * Constructor.
   * 
   * @param msa
   *          the MSA from which to create the consensus sequence
   * @param the
   *          type of Position Specific Matrix used to create the consensus. See
   *          one of the XXX_MATRIX constants defined in PositionSpecificMatrix
   *          class.
   */
  public DefaultConsensModel(DSequenceAlignment msa, String psmType) {
    this();
    msa_ = msa;
    psm_ = msa.getPositionSpecificMatrix(psmType);
  }

  /**
   * Implementation of ConsensusModel interface.
   */
  public void computeConsensus(int threshold) {
    ConsensusCell cell;
    SymbolCounter[] sCounters;
    ArrayList<DSymbol> cons;
    DAlphabet alph;
    DSymbol mainSymbol;
    int i, size, j, length, max;

    if (msa_ == null || msa_.rows() == 0)
      return;
    if (psm_ == null || psm_.columns() == 0)
      return;
    size = psm_.columns();
    if (consensus_ == null) {
      consensus_ = new ConsensusCell[size];
    }
    alph = psm_.getAlphabet();
    for (i = 0; i < size; i++) {
      // by default, show all aligned letters
      sCounters = psm_.getCounter(i).getCounters(threshold, msa_.rows());
      cons = new ArrayList<>();
      mainSymbol = alph.getSymbol(DSymbol.SPACE_SYMBOL_CODE);
      if (sCounters != null) {
        max = 0;
        length = sCounters.length;
        for (j = 0; j < length; j++) {
          cons.add(sCounters[j].getSymbol());
          if (sCounters[j].getCounter() > max) {
            mainSymbol = sCounters[j].getSymbol();
          }
        }
      } else {
        cons.add(alph.getSymbol(DSymbol.SPACE_SYMBOL_CODE));
      }
      cell = consensus_[i];
      if (cell == null) {
        cell = new ConsensusCell(i + 1, cons);
        consensus_[i] = cell;
      } else {
        cell.setConsensus(cons);
      }
      cell.setMainSymbol_(mainSymbol);
    }
  }

  /**
   * Implementation of ConsensusModel interface.
   */
  public ConsensusCell[] getConsensusCells() {
    return consensus_;
  }

  /**
   * Implementation of DSequence interface.
   */
  public DSymbol getSymbol(int idx) {
    return consensus_[idx].getMainSymbol();
  }

  /**
   * Implementation of DSequence interface.
   */
  public int size() {
    return msa_.columns();
  }

  /**
   * Implementation of DSequence interface.
   */
  public DRulerModel createRulerModel(int startPos, int increment) {
    drm_ = new DRulerModelImplem(startPos, startPos + msa_.columns(), increment);
    return drm_;
  }

  /**
   * Implementation of DSequence interface.
   */
  public DRulerModel createRulerModel(int[] coord) {
    drm_ = new DRulerModelImplem(coord);
    return drm_;
  }

  /**
   * Implementation of DSequence interface.
   */
  public DRulerModel getRulerModel() {
    if (drm_ != null)
      return drm_;
    else
      return this.createRulerModel(1, 1);
  }

  /**
   * Implementation of DSequence interface. Always returns null.
   */
  public String toString() {
    return null;
  }

  /**
   * Implementation of DSequence interface.
   */
  public DAlphabet getAlphabet() {
    return psm_.getAlphabet();
  }

  /**
   * Implementation of DSequence interface. Always returns null.
   */
  public List<DLocation> getSequenceParts() {
    return null;
  }

  /**
   * Implementation of DSequence interface. Always returns 0.
   */
  public int getGapContent() {
    return 0;
  }

  /**
   * Implementation of DSequence interface. Always returns null.
   */
  public DSequence getSubSequence(int idxFrom, int idxTo, boolean inverse) {
    return null;
  }

  /**
   * Implementation of DSequence interface.
   */
  public DSequenceInfo getSequenceInfo() {
    return dsi_;
  }

  /**
   * Implementation of DSequence interface.
   */
  public void setSequenceInfo(DSequenceInfo dsi) {
    dsi_ = dsi;
  }
}

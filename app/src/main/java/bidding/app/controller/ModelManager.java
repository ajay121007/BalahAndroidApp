package bidding.app.controller;

/**
 * Created by rishav
 * on 17/1/17.
 */

public class ModelManager {

    private static ModelManager modelManager;
    private LoginManager loginManager;
    private AuctionManager auctionManager;
    private HistoryManager historyManager;
    private ProductManager productManager;
    private ProductDetailManager productDetailManager;
    private CartManager cartManager;
    private SubmitBidManager submitBidManager;
    private UpdateProfileManager updateProfileManager;
    private AddCartManager addCartManager;
    private ProfileDeatilManager profileDeatilManager;
    private AddAuctionManager addAuctionManager;
    private AllAuctionManger allAuctionManger;
    private WinnerDetailManger winnerDetailManger;
    private AuctionDetailsById auctionDetailsById;
    private AuctionOwnerInfo auctionOwnerInfo;


    private ModelManager() {

        winnerDetailManger = new WinnerDetailManger();
        allAuctionManger = new AllAuctionManger();
        loginManager = new LoginManager();
        auctionManager = new AuctionManager();
        historyManager = new HistoryManager();
        productManager = new ProductManager();
        productDetailManager = new ProductDetailManager();
        cartManager = new CartManager();
        submitBidManager = new SubmitBidManager();
        updateProfileManager = new UpdateProfileManager();
        addCartManager = new AddCartManager();
        profileDeatilManager = new ProfileDeatilManager();
        addAuctionManager = new AddAuctionManager();
        auctionDetailsById= new AuctionDetailsById();
        auctionOwnerInfo= new AuctionOwnerInfo();

    }

    public static ModelManager getInstance() {

        if (modelManager == null)
            return modelManager = new ModelManager();
        else
            return modelManager;
    }

    public AuctionDetailsById getAuctionDetailsById() {
        return auctionDetailsById;
    }

    public AllAuctionManger getAllAuctionManger() {
        return allAuctionManger;
    }

    public WinnerDetailManger getWinnerDetailManger() {
        return winnerDetailManger;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public AddAuctionManager getAddAuctionManager() {
        return addAuctionManager;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public ProductDetailManager getProductDetailManager() {
        return productDetailManager;
    }

    public AuctionManager getAuctionManager() {
        return auctionManager;
    }

    public CartManager getCartManager() {
        return cartManager;
    }

    public SubmitBidManager getSubmitBidManager() {
        return submitBidManager;
    }

    public UpdateProfileManager getUpdateProfileManager() {
        return updateProfileManager;
    }


    public AddCartManager getAddCartManager() {
        return addCartManager;
    }

    public ProfileDeatilManager getProfileDeatilManager() {
        return profileDeatilManager;
    }

    public AuctionOwnerInfo getAuctionOwnerInfo() {
        return auctionOwnerInfo;
    }


}

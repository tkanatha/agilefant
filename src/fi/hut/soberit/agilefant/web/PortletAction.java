package fi.hut.soberit.agilefant.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import fi.hut.soberit.agilefant.annotations.PrefetchId;
import fi.hut.soberit.agilefant.business.AgilefantWidgetBusiness;
import fi.hut.soberit.agilefant.business.WidgetCollectionBusiness;
import fi.hut.soberit.agilefant.model.AgilefantWidget;
import fi.hut.soberit.agilefant.model.WidgetCollection;
import fi.hut.soberit.agilefant.security.SecurityUtil;

@Component("portletAction")
@Scope("prototype")
public class PortletAction extends ActionSupport implements CRUDAction, ContextAware, Prefetching {

    private static final long serialVersionUID = -999270161618784027L;

    @PrefetchId
    private int collectionId = 0;
    
    private int objectId = 0;
    
    private WidgetCollection collection;
    
    private WidgetCollection contents;
    private List<List<AgilefantWidget>> widgetGrid = new ArrayList<List<AgilefantWidget>>();

    private List<WidgetCollection> publicCollections = new ArrayList<WidgetCollection>();
    private List<WidgetCollection> privateCollections = new ArrayList<WidgetCollection>();
    
    @Autowired
    private AgilefantWidgetBusiness agilefantWidgetBusiness;
    
    @Autowired
    private WidgetCollectionBusiness widgetCollectionBusiness;
    
    public String retrieve() {
        if (collectionId == 0) {
            return Action.SUCCESS + "_projectPortfolio";
        }
        publicCollections = widgetCollectionBusiness.getAllPublicCollections();
        privateCollections = widgetCollectionBusiness.getCollectionsForUser(SecurityUtil.getLoggedUser());
        contents = widgetCollectionBusiness.retrieve(collectionId); 
        widgetGrid = agilefantWidgetBusiness.generateWidgetGrid(contents, 2);
        return Action.SUCCESS;
    }
    
    public String store() {
        widgetCollectionBusiness.store(collection);
        return Action.SUCCESS;
    }
    
    public String create() {
        collection = widgetCollectionBusiness.createPortfolio();
        collectionId = collection.getId();
        return Action.SUCCESS;
    }

    public String delete() {
        widgetCollectionBusiness.delete(collectionId);
        return Action.SUCCESS;
    }
    

    public void initializePrefetchedData(int objectId) {
        collection = widgetCollectionBusiness.retrieveDetached(objectId);
    }
    
    
    /*
     * AUTOGENERATED LIST OF SETTERS AND GETTERS
     */
    
    public String getContextName() {
        return "portfolio";
    }
    public int getContextObjectId() {
        return collectionId;
    }
    
    public WidgetCollection getContents() {
        return contents;
    }

    public void setContents(WidgetCollection contents) {
        this.contents = contents;
    }

    public List<List<AgilefantWidget>> getWidgetGrid() {
        return widgetGrid;
    }
    
    public int getViewId() {
        return collectionId;
    }
    
    public void setViewId(int viewId) {
        this.collectionId = viewId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public List<WidgetCollection> getAllCollections() {
        return publicCollections;
    }

    public WidgetCollection getCollection() {
        return collection;
    }

    public void setCollection(WidgetCollection collection) {
        this.collection = collection;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public List<WidgetCollection> getPrivateCollections() {
        return privateCollections;
    }

    public List<WidgetCollection> getPublicCollections() {
        return publicCollections;
    }


}

/*
 * Copyright 2015 Igor Maznitsa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.nbmindmap.nb.dataobj;

import java.awt.Image;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

@MIMEResolver.ExtensionRegistration(displayName = "Mind map", mimeType = MMDDataObject.MIME, extension = {MMDDataObject.MMD_EXT})
@DataObject.Registration(iconBase = "com/igormaznitsa/nbmindmap/nb/nbmm16.png", displayName = "Text Mind Map", mimeType = MMDDataObject.MIME)
public class MMDDataObject extends MultiDataObject implements Lookup.Provider{
  private static final long serialVersionUID = -833567211826863321L;

  public static final String MIME = "text/x-mmd+markdown";
  public static final String MMD_EXT = "mmd";
  
  private static final Image NODE_ICON = ImageUtilities.loadImage("com/igormaznitsa/nbmindmap/nb/nbmm16.png");
  
  
  final InstanceContent ic;
  private final AbstractLookup lookup;
  
  public MMDDataObject(final FileObject pf, final MultiFileLoader loader) throws DataObjectExistsException, IOException {
    super(pf, loader);
    registerEditor(MIME, true);
    
    this.ic = new InstanceContent();
    lookup = new AbstractLookup(ic);
    ic.add(MMDEditorSupport.create(this));
    ic.add(this);
  }

  @Override
  protected Node createNodeDelegate() {
    return new DataNode(this, Children.LEAF, this.lookup){
      @Override
      public Image getIcon(final int type) {
        return NODE_ICON;
      }
    };
  }

  @Override
  protected int associateLookup() {
    return 1;
  }

  @Override
  public Lookup getLookup() {
    return this.lookup;
  }

  @Override
  public <T extends Node.Cookie> T getCookie(final Class<T> type) {
    return lookup.lookup(type);
  }

}
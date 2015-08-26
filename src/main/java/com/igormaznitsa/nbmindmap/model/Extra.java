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
package com.igormaznitsa.nbmindmap.model;

import com.igormaznitsa.nbmindmap.utils.Utils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.URISyntaxException;

public abstract class Extra <T> implements Serializable, Constants, Cloneable {
  private static final long serialVersionUID = 2547528075256486018L;

  public enum ExtraType {
    FILE,
    LINK,
    NOTE,
    TOPIC,
    LINE;
    
    public Extra<?> make(final String text) throws URISyntaxException{
      switch(this){
        case FILE : return new ExtraFile(text);
        case LINK : return new ExtraLink(text);
        case NOTE : return new ExtraNote(text);
        case TOPIC : return new ExtraTopic(text);
        case LINE : return new ExtraLine(text);
        default: throw new Error("Unexpected value ["+this.name()+']');
      }
    }
  }
  
  public abstract T getValue();
  public abstract ExtraType getType();
  public abstract void writeContent(Writer out) throws IOException;

  public final void write(final Writer out) throws IOException {
    out.append("- ").append(getType().name()).append(NEXT_LINE);
    writeContent(out);
  }
  
}

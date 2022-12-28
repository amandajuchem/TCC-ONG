import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimaisExcluirComponent } from './animais-excluir.component';

describe('AnimaisExcluirComponent', () => {
  let component: AnimaisExcluirComponent;
  let fixture: ComponentFixture<AnimaisExcluirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimaisExcluirComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimaisExcluirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

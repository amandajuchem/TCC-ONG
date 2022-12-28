import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimaisCadastroComponent } from './animais-cadastro.component';

describe('AnimaisCadastroComponent', () => {
  let component: AnimaisCadastroComponent;
  let fixture: ComponentFixture<AnimaisCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimaisCadastroComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimaisCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
